import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DatePipe } from '@angular/common';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatDatepickerModule, MatNativeDateModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { ClientService } from '../clients.service';
import { CreateClientComponent } from './create-client.component';

import { NgModule } from '@angular/core';

@NgModule({
  imports: [
    MatDatepickerModule,
    MatNativeDateModule,
    BrowserAnimationsModule
  ],
  exports: [
    MatDatepickerModule,
    MatNativeDateModule,
    BrowserAnimationsModule
  ]
})
class MaterialModule {}

describe('CreateClientComponent', () => {
  let component: CreateClientComponent;
  let fixture: ComponentFixture<CreateClientComponent>;
  let mockService: ClientService;
  let formBuilder: FormBuilder;
  let mockRouter: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateClientComponent ],
      imports: [
        HttpClientTestingModule, 
        RouterTestingModule, 
        ReactiveFormsModule,
        MaterialModule
      ],
      providers: [DatePipe]
    })
    .compileComponents();
  });

  beforeEach(() => {
    mockService = TestBed.inject(ClientService);
    formBuilder = TestBed.inject(FormBuilder);
    mockRouter = TestBed.inject(Router);
    fixture = TestBed.createComponent(CreateClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

// describe('CreateClientComponent', () => {
//   let component: CreateClientComponent;
//   let fixture: ComponentFixture<CreateClientComponent>;
//   let mockService: ClientService;
//   let formBuilder: FormBuilder;
//   let mockRouter: Router;

//   beforeEach(async () => {
//     await TestBed.configureTestingModule({
//       declarations: [ CreateClientComponent ],
//       imports: [
//         HttpClientTestingModule, 
//         RouterTestingModule, 
//         ReactiveFormsModule,
//         MatDatepickerModule,
//         MatNativeDateModule,
//         BrowserAnimationsModule
//       ],
//       providers: [DatePipe]
//     })
//     .compileComponents();
//   });

//   beforeEach(() => {
//     mockService = TestBed.inject(ClientService);
//     formBuilder = TestBed.inject(FormBuilder);
//     mockRouter = TestBed.inject(Router)
//     fixture = TestBed.createComponent(CreateClientComponent);
//     component = fixture.componentInstance;
//     fixture.detectChanges();
//   });

//   it('should create', () => {
//     expect(component).toBeTruthy();
//   });

//   describe('ngOnInit', () => {
//     it('should create the form with default values', () => { 

//       const formGroup = formBuilder.group({
//         registrationType: [RegistrationType.CNPJ],
//         registrationNumber: [''],
//         clientCode: [''],
//         fantasyName: [''],
//         registrationDate: [new Date().toISOString()],
//         companyStatus: [CompanyStatus.ACTIVE],
//         accountantId: [''],
//       });
      
//       Act
//       component.ngOnInit();

//       Assert
//       expect(component.clientForm.value).toEqual(formGroup.value);
//     });

//     it ('should have the validators applied to each form control', () => {
//       // Arrange
//       const registrationNumber = component.accountantForm.get('registrationNumber');
//       const accountantCode = component.accountantForm.get('accountantCode');
//       const name = component.accountantForm.get('name');
//       const isActive = component.accountantForm.get('isActive');

//       // Act
//       component.ngOnInit();
//       registrationNumber!.setValue('');
//       accountantCode!.setValue('');
//       name!.setValue('');
//       isActive!.setValue('');

//       // Assert
//       expect(registrationNumber!.errors).toEqual({required: true, cpfInvalid: true});
//       expect(accountantCode!.errors).toEqual({required: true});
//       expect(name!.errors).toEqual({required: true});
//       expect(isActive!.errors).toEqual({required: true});
//     });
//   });
// });
