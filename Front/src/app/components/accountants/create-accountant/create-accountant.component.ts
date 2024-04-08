import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AccountantService } from '../accountants.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-accountant',
  templateUrl: './create-accountant.component.html',
  styleUrls: ['./create-accountant.component.css']
})
export class CreateAccountantComponent implements OnInit {
  accountantForm!: FormGroup<any>;

  constructor(    
    //dependencias
    private service: AccountantService, //configurar as ações do CRUD (criar, deletar)
    private router: Router, //Redirecionamento
    private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    //validações todas aqui? perguntar sobre a mascara que tinha comentado na quinta
    this.accountantForm = this.formBuilder.group({
      registrationNumber: ['', Validators.compose([
        Validators.required
      ])],
      accountantCode: ['', Validators.compose([
        Validators.required
      ])],
      name: ['', Validators.compose([
        Validators.required,
        Validators.maxLength(250) //falta fazer mostrar o erro em tela
      ])],
      isActive: [true]
    })
  }

  createAccountant() {
    if(this.accountantForm.valid){
      this.service.createAccountant(this.accountantForm.value).subscribe(() => {
      })
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
    this.router.navigate(['/listAccountants'])
  }
}

