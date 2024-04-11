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
  accountantForm!: FormGroup;

  constructor(    
    //dependencias
    private service: AccountantService, //configurar as ações do CRUD (criar, deletar)
    private router: Router, //Redirecionamento
    private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.accountantForm = this.formBuilder.group({
      registrationNumber: ['', Validators.compose([
        Validators.required
      ])],
      accountantCode: ['', Validators.compose([
        Validators.required
      ])],
      name: ['', Validators.compose([
        Validators.required,
        Validators.maxLength(250)
      ])],
      isActive: [true]
    })
  }

  
  async createAccountant() {
    if (this.accountantForm.valid) {
      await this.service.create(this.accountantForm.value).toPromise();
      this.router.navigate(['/listAccountants']);
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

