import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DateAdapter } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, Observable, scan, tap } from 'rxjs';
import { Accountant } from '../../accountants/accountant';
import { FormValidators } from '../../validators/form-validators';
import { ClientService } from '../clients.service';
import { CompanyStatus } from '../companyStatus';
import { RegistrationType } from '../registrationType';

@Component({
  selector: 'app-edit-client',
  templateUrl: './edit-client.component.html',
  styleUrls: ['./edit-client.component.css']
})
export class EditClientComponent implements OnInit {
  clientForm!: FormGroup;
  registrationType: RegistrationType = RegistrationType.CNPJ;
  registrationPlaceholder: string = 'Digite o CNPJ';
  mask: string;
  companyStatus: CompanyStatus;
  picker: string = 'picker';
  errorMessage: string = '';
  registrationDate: Date;
  total = 100;
  data = Array.from({length: this.total}).map((_, i) => `Option ${i}`);
  clientLoadLimit = 10;
  clientLoadOffset = 0;
  accountantId: number | undefined;
  accountants = new BehaviorSubject<Accountant[]>([]);
  accountants$: Observable<Accountant[]>;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private service: ClientService, 
    private formBuilder: FormBuilder,
    private adapter: DateAdapter<Date>) {
      this.accountants$ = this.accountants.asObservable().pipe(
        scan((acc:Accountant[], curr:Accountant[]) => {
          return [...acc, ...curr];
        }, [])
      ),
      this.adapter.setLocale('pt-BR');
     }

  ngOnInit(): void {
    this.clientForm = this.formBuilder.group({
      registrationType: [''],
      registrationNumber: [''],
      clientCode: [''],
      name: [''],
      fantasyName: [''],
      registrationDate: [''],
      companyStatus: [''],
      accountantId: ['']
    });
  
    const id = this.route.snapshot.paramMap.get('id');
    this.service.searchClientByID(parseInt(id!)).subscribe(client => {
      


      if (client.accountant && client.accountant.id) {
        this.accountantId = client.accountant && client.accountant.id;
        if (this.accountantId) {
          this.getNextBatch(this.accountantId).subscribe(accountants => {
            // for loop that checks if there is any accountant in the list with the same id as the client's accountant
            for (let i = 0; i < accountants.length; i++) {
              if (accountants[i].id === this.accountantId) {
                // do nothing if the accountant is already in the list
              } else {
                // search for the accountant by id and add it to the list
                this.service.searchAccountantByID(34).subscribe(accountant => {
                  this.accountants.next([...accountants, accountant]);
                });
              }
            }
          });
        }
      }
    



      this.clientForm = this.formBuilder.group({
        id: [client.id],
        registrationType: [client.registrationType, Validators.compose([
          Validators.required
        ])],
        registrationNumber: [client.registrationNumber, Validators.compose([
          Validators.required,
          FormValidators.cnpjValidator
        ])],
        clientCode: [client.clientCode, Validators.compose([
          Validators.required
        ])],
        name: [client.name, Validators.compose([
          Validators.required,
          Validators.maxLength(250)
        ])],
        fantasyName: [client.fantasyName, Validators.compose([
          Validators.maxLength(250)
        ])],
        registrationDate: [client.registrationDate, Validators.compose([
          Validators.required
        ])],
        companyStatus: [client.companyStatus, Validators.compose([
          Validators.required
        ])],
        accountantId: [client.accountant.id, Validators.compose([
          Validators.required
        ])]
      });
      this.onRegistrationTypeChange(client.registrationType);
    });
  }

  onRegistrationTypeChange(type: RegistrationType) {
    this.registrationType = type;
    this.mask = type === RegistrationType.CPF ? '000.000.000-00' : '00.000.000/0000-00';
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

  editClient() {
    if (this.clientForm.valid) {
 
      this.service.edit(this.clientForm.value).subscribe({
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

  getNextBatch(accountantId: number | undefined): Observable<Accountant[]> {
    this.service.listAccountantsData('', this.clientLoadOffset, this.clientLoadLimit).subscribe(response => {
      this.accountants.next(response.content);
      this.clientLoadOffset++;
      this.total = response.totalElements;
    });
    return this.accountants$;
  }

}
