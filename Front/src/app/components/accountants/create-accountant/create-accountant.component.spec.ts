import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed, fakeAsync } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { AccountantService } from '../accountants.service';
import { CreateAccountantComponent } from './create-accountant.component';

describe('CreateAccountantComponent', () => {
  let component: CreateAccountantComponent;
  let fixture: ComponentFixture<CreateAccountantComponent>;
  let mockService: AccountantService;
  let formBuilder: FormBuilder;
  let mockRouter: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateAccountantComponent],
      imports: [HttpClientTestingModule, RouterTestingModule, ReactiveFormsModule],
    })
    .compileComponents();
  });

  beforeEach(() => {
    mockService = TestBed.inject(AccountantService);
    formBuilder = TestBed.inject(FormBuilder);
    mockRouter = TestBed.inject(Router)
    fixture = TestBed.createComponent(CreateAccountantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should create the form with default values', () => { 
      // Arrange
      const formGroup = formBuilder.group({
        registrationNumber: [''],
        accountantCode: [''],
        name: [''],
        isActive: [true]
      });
      
      // Act
      component.ngOnInit();

      // Assert
      expect(component.accountantForm.value).toEqual(formGroup.value);
    });

    it ('should have the validators applied to each form control', () => {
      // Arrange
      const registrationNumber = component.accountantForm.get('registrationNumber');
      const accountantCode = component.accountantForm.get('accountantCode');
      const name = component.accountantForm.get('name');
      const isActive = component.accountantForm.get('isActive');

      // Act
      component.ngOnInit();
      registrationNumber!.setValue('');
      accountantCode!.setValue('');
      name!.setValue('');
      isActive!.setValue('');

      // Assert
      expect(registrationNumber!.errors).toEqual({required: true, cpfInvalid: true});
      expect(accountantCode!.errors).toEqual({required: true});
      expect(name!.errors).toEqual({required: true});
      expect(isActive!.errors).toEqual({required: true});
    });
  });

  describe('createAccountant', () => {
    it('should call the service create method and navigate to the listAccountants route when the form is valid', () => {
      // Arrange
      component.accountantForm = formBuilder.group({
        registrationNumber: ['78868927047'],
        accountantCode: ['123'],
        name: ['Teste'],
        isActive: [true]
      });
      jest.spyOn(mockService, 'create').mockReturnValue({
        subscribe: (callbacks: any) => {
          callbacks.next();
          return { unsubscribe: () => {} };
        }
      } as any); //simula o retorno do subscribe como o next
      jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
      
      // Act
      component.createAccountant();

      // Assert
      expect(mockService.create).toHaveBeenCalledWith(component.accountantForm.value);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['/accountants','listAccountants']);
    });

    
    it('should set the errorMessage to "Registro duplicado" when the status is 409', fakeAsync(() => {
      // Arrange
      component.accountantForm = formBuilder.group({
        registrationNumber: ['78868927047'],
        accountantCode: ['123'],
        name: ['Teste'],
        isActive: [true]
      });
      jest.spyOn(mockService, 'create').mockReturnValue({
        subscribe: (callbacks: any) => {
          callbacks.error({ status: 409 });
          return { unsubscribe: () => {} };
        }
      } as any);

      // Act
      component.createAccountant();

      // Assert
      expect(component.errorMessage).toEqual('Registro duplicado');
    }));

    it('should set the errorMessage to "Erro inesperado" when the status is not 409', fakeAsync(() => {
      // Arrange
      component.accountantForm = formBuilder.group({
        registrationNumber: ['78868927047'],
        accountantCode: ['123'],
        name: ['Teste'],
        isActive: [true]
      });
      jest.spyOn(mockService, 'create').mockReturnValue({
        subscribe: (callbacks: any) => {
          callbacks.error({ status: 500 });
          return { unsubscribe: () => {} };
        }
      } as any);

      // Act
      component.createAccountant();

      // Assert
      expect(component.errorMessage).toEqual('Erro inesperado');
    }));
  });

  describe('btnEnable', () => {
    it('should return btn-save when the form is valid', () => {
      // Arrange
      component.ngOnInit();
      component.accountantForm.get('registrationNumber')!.setValue('82454950006');
      component.accountantForm.get('accountantCode')!.setValue('123');
      component.accountantForm.get('name')!.setValue('Teste');
      component.accountantForm.get('isActive')!.setValue(true);

      // Act
      const result = component.btnEnable();

      // Assert
      expect(result).toEqual('btn-save');
    })

    it('should return btn-disabled when the form is NOT valid', () => {
      // Arrange
     component.ngOnInit();

      // Act
      const result = component.btnEnable();

      // Assert
      expect(result).toEqual('btn-disabled');
    });
  });

  describe('cancelAccountant', () => {
    it('should call router.navigate with /accountants,listAccountants as the path', () => {
      // Arrange
      jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));

      // Act
      component.cancelAccountant();

      // Assert
      expect(mockRouter.navigate).toHaveBeenCalledWith(['/accountants','listAccountants']);
    });
  });

  describe('getErrorMessage', () => {
    it('should return "Este campo é obrigatório." when the control.erros is equal to required', () => {
      // Arrange
      component.ngOnInit();
      component.accountantForm.get('registrationNumber')!.setValue('');

      // Act
      const result = component.getErrorMessage('registrationNumber');

      // Assert
      expect(result).toEqual('Este campo é obrigatório.');
    });

    it('should return "CPF inválido." when the control.erros is equal to cpfInvalid', () => {
      // Arrange
      component.ngOnInit();
      component.accountantForm.get('registrationNumber')!.setValue('123');

      // Act
      const result = component.getErrorMessage('registrationNumber');

      // Assert
      expect(result).toEqual('CPF inválido.');
    });

    it('should return null when the control.erros is null', () => {
      // Arrange
      component.ngOnInit();
      component.accountantForm.get('registrationNumber')!.setValue('82454950006');

      // Act
      const result = component.getErrorMessage('registrationNumber');

      // Assert
      expect(result).toBeNull();
    });
  });
});
