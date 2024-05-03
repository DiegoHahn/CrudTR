import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AccountantService } from '../accountants.service';
import { Router } from '@angular/router';
import { FormValidators } from '../../validators/form-validators';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-create-accountant',
  templateUrl: './create-accountant.component.html',
  styleUrls: ['./create-accountant.component.css']
})
export class CreateAccountantComponent implements OnInit {
  accountantForm!: FormGroup;
  errorMessage: string = '';

  constructor(    
    private service: AccountantService, 
    private router: Router,
    private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.accountantForm = this.formBuilder.group({
      registrationNumber: ['', Validators.compose([
        Validators.required,
        //validador personalizado
        FormValidators.cpfValidator],
      )],
      accountantCode: ['', Validators.required],
      name: ['', Validators.compose([
        Validators.required,
        Validators.maxLength(250)
      ])],
      isActive: [true, Validators.required]
    });
  }

  createAccountant() {
    if (this.accountantForm.valid) {
      this.service.create(this.accountantForm.value).subscribe({
        next: () => {
          this.router.navigate(['/accountants','listAccountants']);
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

  btnEnable(): string {
    if(this.accountantForm.valid) {
      return 'btn-save'
    } else {
      return 'btn-disabled'
    }
  }

  cancelAccountant(){
    this.router.navigate(['/accountants','listAccountants'])
  }

  //recebe o nome do campo do formulário e gera a mensagem de erro
  getErrorMessage(controlName: string): string | null {
    const control = this.accountantForm.get(controlName);
    if (control?.errors?.['required']) {
      return 'Este campo é obrigatório.';
    } else if (control?.errors?.['cpfInvalid']) {
      return 'CPF inválido.';
    }
    return null;
  }
  
}

