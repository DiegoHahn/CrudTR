import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FormValidators } from '../../validators/form-validators';
import { HttpErrorResponse } from '@angular/common/http';
import { ClientService } from '../clients.service';
import { CompanyStatus } from '../companyStatus';
import { RegistrationType } from '../registrationType';
import { DateAdapter } from '@angular/material/core';



@Component({
  selector: 'app-create-client',
  templateUrl: './create-client.component.html',
  styleUrls: ['./create-client.component.css']
})
export class CreateClientComponent implements OnInit {

  clientForm!: FormGroup;
  registrationType: RegistrationType = RegistrationType.CNPJ;
  registrationPlaceholder: string;
  mask: string;
  companyStatus: CompanyStatus = CompanyStatus.ACTIVE;
  picker: string = 'picker';
  registrationDate = new Date();
  // registrationDateString: string = this.registrationDate.toISOString().split('T')[0];

  constructor(
    private service: ClientService, 
    private router: Router,
    private formBuilder: FormBuilder,
    //formatar a data para o padrão brasileiro
    private adapter: DateAdapter<any>
  ) {
    this.adapter.setLocale('pt-BR');
  }

  ngOnInit(): void {
    this.clientForm = this.formBuilder.group({
      registrationType: ['', Validators.compose([
        Validators.required,
        FormValidators.cpfValidator],
      )],
      registrationNumber: ['', Validators.compose([
        Validators.required],
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
      registrationDate: ['', Validators.compose([
        Validators.required,
      ])],
      companyStatus: ['', Validators.compose([
        Validators.required,
      ])]
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
}


//   createAccountant() {
//     if (this.accountantForm.valid) {
//       this.service.create(this.accountantForm.value).subscribe({
//         next: () => {
//           this.router.navigate(['/accountants','listAccountants']);
//         },
//         error: (error: HttpErrorResponse) => {
//           console.log(error);
//           if (error.status === 409) {
//             this.errorMessage = "Registro duplicado";
//           } else {
//             this.errorMessage = "Erro inesperado";
//           }
//         }
//       });
//     }
//   }

//   btnEnable(): string {
//     if(this.accountantForm.valid) {
//       return 'btn-save'
//     } else {
//       return 'btn-disabled'
//     }
//   }

//   cancelAccountant(){
//     this.router.navigate(['/accountants','listAccountants'])
//   }

//   //recebe o nome do campo do formulário e gera a mensagem de erro
//   getErrorMessage(controlName: string): string | null {
//     const control = this.accountantForm.get(controlName);
//     if (control?.errors?.['required']) {
//       return 'Este campo é obrigatório.';
//     } else if (control?.errors?.['cpfInvalid']) {
//       return 'CPF inválido.';
//     }
//     return null;
//   }
  
// }

