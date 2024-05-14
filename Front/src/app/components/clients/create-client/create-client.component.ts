import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material';
import { DateAdapter } from '@angular/material/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, scan } from 'rxjs';
import { Accountant } from '../../accountants/accountant';
import { FormValidators } from '../../validators/form-validators';
import { ClientService } from '../clients.service';
import { CompanyStatus } from '../companyStatus';
import { RegistrationType } from '../registrationType';

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
  errorMessage: string = '';
  registrationDate: Date  = new Date();
  //variáveis para paginação
  total = 100;
  data = Array.from({length: this.total}).map((_, i) => `Option ${i}`);
  clientLoadLimit = 10;
  clientLoadOffset = 0;
  accountants = new BehaviorSubject<Accountant[]>([]);
  accountants$: Observable<Accountant[]>;

  constructor(
    private datePipe: DatePipe,
    private service: ClientService, 
    private router: Router,
    private formBuilder: FormBuilder,
    private adapter: DateAdapter<Date>
  ) {
    this.accountants$ = this.accountants.asObservable().pipe(
      scan((acc:Accountant[], curr:Accountant[]) => {
        return [...acc, ...curr];
      }, [])
    ),
    
    //formatar a data para o padrão brasileiro
    this.adapter.setLocale('pt-BR');
  }

  ngOnInit(): void {
    this.getNextBatch();
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
        Validators.maxLength(250)
      ])],
      registrationDate: [this.registrationDate.toISOString(), Validators.compose([
        Validators.required,
      ])],
      companyStatus: [this.companyStatus, Validators.compose([
        Validators.required,
      ])],
      accountantId: ['', Validators.compose([
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


  mostra(){
    console.log(this.clientForm.value)
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

  getNextBatch() {
    this.service.listAccountantsData('', this.clientLoadOffset, this.clientLoadLimit).subscribe(response => {
      this.accountants.next(response.content);
      this.clientLoadOffset += this.clientLoadLimit;
      this.total = response.totalElements;
    });
  }

  onDateSelect(event: MatDatepickerInputEvent<Date>): void {
    this.clientForm.patchValue({registrationDate: this.datePipe.transform(event.value, 'dd/MM/yyyy')});
  }
}


