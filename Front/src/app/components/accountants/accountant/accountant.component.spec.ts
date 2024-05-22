import { TemplateRef } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Accountant } from '../accountant';
import { AccountantComponent } from './accountant.component';

describe('AccountantComponent', () => {
  let component: AccountantComponent;
  let fixture: ComponentFixture<AccountantComponent>;

  beforeEach(async () => {
    // Configuração do módulo de teste 
    await TestBed.configureTestingModule({
      declarations: [ AccountantComponent ]
    })
    .compileComponents();
  });
  
  beforeEach(() => {
    fixture = TestBed.createComponent(AccountantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // Teste de criação do componente
  it('should create', () => {
    expect(component).toBeTruthy();
  });
  // Teste para verificar se a entrada de listAccountants é aceita
  it('should accept listAccountants input', () => {
    const accountants: Accountant[] = [
      { name: "teste1", registrationNumber: "10832323222", accountantCode: "123", isActive: true },
      { name: "teste2", registrationNumber: "12345678901", accountantCode: "123", isActive: false }
    ];
    component.listAccountants = accountants;
    expect(component.listAccountants).toEqual(accountants);
  });
  // Teste para verificar se a referência do template de ação é definida
  it('should set action template reference', () => {
    const templateRef: TemplateRef<Accountant> = {} as TemplateRef<Accountant>;
    component.actionTemplateRef = templateRef;
    expect(component.actionTemplateRef).toBe(templateRef);
  });
});