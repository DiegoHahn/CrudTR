import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FormValidators } from '../../validators/form-validators';
import { HttpErrorResponse } from '@angular/common/http';
import { ClientService } from '../clients.service';
import { CompanyStatus } from '../companyStatus';
import { RegistrationType } from '../registrationType';
import { DateAdapter } from '@angular/material/core';
import { Accountant } from '../../accountants/accountant';

@Component({
  selector: 'app-create-client',
  templateUrl: './create-client.component.html',
  styleUrls: ['./create-client.component.css']
})
export class CreateClientComponent implements OnInit {

  clientForm!: FormGroup;
  registrationType: RegistrationType = RegistrationType.CNPJ;
  registrationPlaceholder: string = 'Digite o CNPJ';
  mask: string = '00.000.000/0000-00';
  companyStatus: CompanyStatus = CompanyStatus.ACTIVE;
  picker: string = 'picker';
  accountants: Accountant[] = [];
  errorMessage: string = '';
  registrationDate: Date = new Date();

  constructor(
    private service: ClientService, 
    private router: Router,
    private formBuilder: FormBuilder,
    //formatar a data para o padrão brasileiro
    private adapter: DateAdapter<Date>
  ) {
    this.adapter.setLocale('pt-BR');
  }

  ngOnInit(): void {

    this.service.getAccountants().subscribe(
      (data) => {
        this.accountants = data;
      });

    this.clientForm = this.formBuilder.group({
      registrationType: [RegistrationType.CNPJ, Validators.compose([
        Validators.required],
      )],
      registrationNumber: ['', Validators.compose([
        Validators.required,
        FormValidators.cnpjValidator
        ],
      )],
      clientCode: ['', Validators.required],
      name: ['', Validators.compose([
        Validators.required,
        Validators.maxLength(250)
      ])],
      fantasyName: ['', Validators.compose([
        Validators.required,
        Validators.maxLength(250)
      ])],
      registrationDate: [this.registrationDate, Validators.compose([
        Validators.required,
      ])],
      companyStatus: [this.companyStatus, Validators.compose([
        Validators.required,
      ])],
      accountant: ['', Validators.compose([
        Validators.required,
      ])],
    });
  }

  //muda o tipo de registro a máscara do campo de registro e validação
  onRegistrationTypeChange(type: RegistrationType) {
    this.registrationType = type;
    this.mask = type === RegistrationType.CPF ? '000.000.000-00' : '00.000.000/0000-00';
    this.clientForm.get('registrationNumber')?.setValue('');
    this.registrationPlaceholder = type === 'CPF' ? 'Digite o CPF' : 'Digite o CNPJ';
    this.clientForm.get('registrationNumber')?.setValidators([
      type === RegistrationType.CPF ? FormValidators.cpfValidator : FormValidators.cnpjValidator
    ]);
  }

  getErrorMessage(controlName: string): string | null {
    const control = this.clientForm.get(controlName)
    if (!control?.errors) {
      return null;
    }

    switch (true) {
      case !!control.errors['required']:
        return 'Este campo é obrigatório.';
      case !!control.errors['cpfInvalid']:
        return 'CPF inválido.';
      case !!control.errors['cnpjInvalid']:
        return 'CNPJ inválido.';
      default:
        return null;
    }
  }

  btnEnable(): string {
    if(this.clientForm.valid) {
      return 'btn-save'
    } else {
      return 'btn-disabled'
    }
  }

  createClient() {
    if (this.clientForm.valid) {
      this.service.create(this.clientForm.value).subscribe({
        next: () => {
          this.router.navigate(['/clients','listClients']);
        },
        error: (error: HttpErrorResponse) => {
          console.log(error);
          if (error.status === 409) {
            this.errorMessage = "Registro duplicado";
          } else {
            this.errorMessage = "Erro inesperado";
          }
        }
      });
    }
  }

  cancelClient(){
    this.router.navigate(['/clients','listClients'])
  }
}


