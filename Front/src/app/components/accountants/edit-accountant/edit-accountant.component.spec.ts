import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed, fakeAsync } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import { FormValidators } from '../../validators/form-validators';
import { AccountantService } from '../accountants.service';
import { EditAccountantComponent } from './edit-accountant.component';

describe('EditAccountantComponent', () => {
  let component: EditAccountantComponent;
  let fixture: ComponentFixture<EditAccountantComponent>;
  let mockService: AccountantService;
  let formBuilder: FormBuilder;
  let mockRouter: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditAccountantComponent],
      imports: [HttpClientTestingModule, RouterTestingModule, ReactiveFormsModule],
    })
    .compileComponents();
  });

  beforeEach(() => {
    mockService = TestBed.inject(AccountantService);
    formBuilder = TestBed.inject(FormBuilder);
    mockRouter = TestBed.inject(Router)
    fixture = TestBed.createComponent(EditAccountantComponent);
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
        id: [null],
        registrationNumber: [''],
        accountantCode: [''],
        name: [''],
        isActive: ['']
      });
      
      // Act
      component.ngOnInit();

      // Assert
      expect(component.accountantForm.value).toEqual(formGroup.value);
    });

    it('should call the service to get the accountant by id', fakeAsync(() => {

      // Arrange
      const id = 1;
      const accountant = {
        id: 1,
        registrationNumber: '12345678909',
        accountantCode: '123456',
        name: 'Accountant Name',
        isActive: true
      };
      jest.spyOn(mockService, 'searchAccountByID').mockReturnValue(of(accountant));

      // Act
      component.ngOnInit();
      mockService.searchAccountByID(id).subscribe();

      // Assert
      expect(mockService.searchAccountByID).toHaveBeenCalledWith(id);
      expect(component.accountantForm.value).toEqual(accountant);

    }));

    describe('editAccountant', () => {
      it('should call the service edit method and navigate to the listAccountants route when the form is valid', fakeAsync(() => {
        // Arrange
        component.accountantForm = formBuilder.group({
          id: ['1'],
          registrationNumber: ['94977122003', Validators.compose([
            Validators.required,
            FormValidators.cpfValidator
          ])],
          accountantCode: ['94977122003', Validators.compose([
            Validators.required
          ])],
          name: ['teste', Validators.compose([
            Validators.required,
            Validators.maxLength(250)
          ])],
          isActive: [true, Validators.compose([
            Validators.required
          ])]
        })
        jest.spyOn(mockService, 'edit').mockReturnValue(of(component.accountantForm.value));
        jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
        
        // Act
        component.editAccountant();
  
        // Assert
        expect(mockService.edit).toHaveBeenCalledWith(component.accountantForm.value);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['/accountants','listAccountants']);
      }));

      it('should log the error message in the console when there is an error', fakeAsync(() => {
        //Arrange
        const mockError = { status: 500 };
        jest.spyOn(console, 'log');
        jest.spyOn(mockService, 'edit').mockReturnValue(throwError(() => mockError));

        //Act
        component.editAccountant();

        // Assert
        expect(console.log).toHaveBeenCalledWith(mockError);
      }))
    });

    describe('btnEnable', () => {
      it('should return btn-save when the form is valid', () => {
        // Arrange
        component.ngOnInit();

        // Act
        const result = component.btnEnable();
  
        // Assert
        expect(result).toEqual('btn-save');
      })
  
      it('should return btn-disabled when the form is NOT valid', () => {
        // Arrange
        component.accountantForm = formBuilder.group({
          id: ['1'],
          registrationNumber: ['', Validators.compose([
            Validators.required,
            FormValidators.cpfValidator
          ])]
        })
   
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
        component.accountantForm = formBuilder.group({
          id: ['1'],
          registrationNumber: ['', Validators.compose([
            Validators.required,
            FormValidators.cpfValidator
          ])]
        })
    
        // Act
        const result = component.getErrorMessage('registrationNumber');
  
        // Assert
        expect(result).toEqual('Este campo é obrigatório.');
      });
  
      it('should return "CPF inválido." when the control.erros is equal to cpfInvalid', () => {
        // Arrange
        component.ngOnInit();
        component.accountantForm = formBuilder.group({
          id: ['1'],
          registrationNumber: ['123', Validators.compose([
            Validators.required,
            FormValidators.cpfValidator
          ])]
        })
  
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
});
