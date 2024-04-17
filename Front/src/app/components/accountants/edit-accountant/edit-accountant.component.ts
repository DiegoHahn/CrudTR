import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AccountantService } from '../accountants.service';


@Component({
  selector: 'app-edit-accountant',
  templateUrl: './edit-accountant.component.html',
  styleUrls: ['./edit-accountant.component.css']
})
export class EditAccountantComponent implements OnInit {
  accountantForm!: FormGroup;

  constructor(  
    private service: AccountantService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    //inicia o formulário com os campos vazios porque se não ocorre o erro de instanciar o form antes da resposta do servidor
    this.accountantForm = this.formBuilder.group({
      id: [null],
      registrationNumber: [''],
      accountantCode: [''],
      name: [''],
      isActive: ['']
    });

    const id = this.route.snapshot.paramMap.get('id');
    this.service.searchAccountByID(parseInt(id!)).subscribe(accountant => {
      this.accountantForm = this.formBuilder.group({
        id: [accountant.id],
        registrationNumber: [accountant.registrationNumber, Validators.compose([
          Validators.required
        ])],
        accountantCode: [accountant.accountantCode, Validators.compose([
          Validators.required
        ])],
        name: [accountant.name, Validators.compose([
          Validators.required,
          Validators.maxLength(250)
        ])],
        isActive: [accountant.isActive]
      })
    });
  }

  editAccountant() {
    if (this.accountantForm.valid) {
      this.service.edit(this.accountantForm.value).subscribe({
        next: () => {
          this.router.navigate(['/listAccountants']);
        },
        error: (error) => {
          console.log(error);
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
    this.router.navigate(['/listAccountants'])
  }
  
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
