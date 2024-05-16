import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DateAdapter } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, Observable, scan } from 'rxjs';
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
  accountantId: number;
  accountants = new BehaviorSubject<Accountant[]>([]);
  accountants$: Observable<Accountant[]>;
  isLoading: boolean;

  constructor( 
    private router: Router,
    private route: ActivatedRoute,
    private service: ClientService, 
    private formBuilder: FormBuilder,
    private adapter: DateAdapter<Date>) {
      this.accountants$ = this.accountants.asObservable().pipe(
        scan((accountants:Accountant[], currentAccountants:Accountant[]) => {
          return [...accountants, ...currentAccountants];
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
        this.accountantId = client.accountant.id;
        this.getNextBatch().subscribe(accountants => {
          const accountantExists = accountants.some(accountant => accountant.id === this.accountantId);
          if (!accountantExists) {
            this.service.searchAccountantByID(this.accountantId!).subscribe(accountant => {
              this.accountants.next([...this.accountants.getValue(), accountant]);
            });
          }
        });
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

  getNextBatch(): Observable<Accountant[]> {
    this.service.listAccountantsData('', this.clientLoadOffset, this.clientLoadLimit).subscribe(response => {
      this.accountants.next(response.content);
      this.clientLoadOffset++;
      this.total = response.totalElements;
    });
    return this.accountants$;
    }
}