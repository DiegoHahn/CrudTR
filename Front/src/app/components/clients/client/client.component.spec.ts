import { CompanyStatus } from './../companyStatus';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TemplateRef } from '@angular/core';
import { ClientComponent } from './client.component';
import { Client } from '../client';
import { RegistrationType } from '../registrationType';

describe('ClientComponent', () => {
  let component: ClientComponent;
  let fixture: ComponentFixture<ClientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

   // Teste para verificar se a entrada de listAccountants é aceita
   it('should accept listClients input', () => {
    const clients: Client[] = [
      {registrationType:RegistrationType.CPF, registrationNumber:"34388376094", clientCode:"123", name:"Cliente1 ", fantasyName:"Cliente1 Ltda", registrationDate:"2024-04-18T17:56:46.970Z", companyStatus:CompanyStatus.ACTIVE, accountantId:52, accountant: { name: "teste1", registrationNumber: "59023624076", accountantCode: "123", isActive: false }},
      {registrationType:RegistrationType.CNPJ, registrationNumber:"48719378000176", clientCode:"1223", name:"Cliente2 ", fantasyName:"Cliente2 Ltda", registrationDate:"2024-07-18T17:56:46.970Z", companyStatus:CompanyStatus.INACTIVE, accountantId:23, accountant: { name: "teste2", registrationNumber: "21243735015", accountantCode: "123", isActive: false }}
    ];
    component.listClients = clients;
    expect(component.listClients).toEqual(clients);
  });
  // Teste para verificar se a referência do template de ação é definida
  it('should set action template reference', () => {
    const templateRef: TemplateRef<Client> = {} as TemplateRef<Client>;
    component.actionTemplateRef = templateRef;
    expect(component.actionTemplateRef).toBe(templateRef);
  });
});
