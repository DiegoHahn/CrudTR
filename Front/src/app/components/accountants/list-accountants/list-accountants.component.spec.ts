import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { AccountantService } from '../accountants.service';
import { ListAccountantsComponent } from './list-accountants.component';
import { HttpErrorResponse } from '@angular/common/http';
import { PageEvent } from '@angular/material';

describe('ListAccountantsComponent', () => {
  let component: ListAccountantsComponent;
  let fixture: ComponentFixture<ListAccountantsComponent>;
  let mockService: AccountantService;

  beforeEach(async () => {    
    await TestBed.configureTestingModule({
      declarations: [ListAccountantsComponent],
      imports: [HttpClientTestingModule]
    })
    .compileComponents();
  });

  beforeEach(() => {
    mockService = TestBed.inject(AccountantService);
    fixture = TestBed.createComponent(ListAccountantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should call loadAccountants, subscribe to onKeyDown and call filterByName after debounce time', fakeAsync(() => {
      // Arrange
      jest.spyOn(component, 'loadAccountants').mockImplementation(() => {});
      jest.spyOn(component, 'filterByName').mockImplementation(() => {});
      const mockKeyboardEvent = new KeyboardEvent("keydown", {
        key: "Enter"
      });
      const pageIndex = component.pageIndex;
      const pageSize = component.pageSize;
      
      // Act
      component.ngOnInit();
      component.onKeyDown.next(mockKeyboardEvent); // Emita um valor válido para o Subject
      tick(600); // Simula a passagem de 600ms
  
      // Assert
      expect(component.loadAccountants).toHaveBeenCalledWith(pageIndex, pageSize);
      expect(component.filterByName).toHaveBeenCalled();
    }));
  });

  describe('loadAccountants', () => {
    it('should call listAccountantsData and set listAccountants and totalElements', () => {
      // Arrange
      const response: any = {
        content: ['accountant1', 'accountant2'],
        totalElements: 2
      };
      jest.spyOn(mockService, 'listAccountantsData').mockReturnValue(of(response));
      const pageIndex = 0;
      const pageSize = 10;
    
      // Act
      component.loadAccountants(pageIndex, pageSize);
    
      // Assert
      expect(mockService.listAccountantsData).toHaveBeenCalledWith(component.nameFilter, pageIndex, pageSize);
      expect(component.listAccountants).toEqual(response.content);
      expect(component.totalElements).toEqual(response.totalElements);
    });
  });
  
  describe('changePage', () => {
    it ('should call loadAccountants', () => {
      // Arrange
      jest.spyOn(component, 'loadAccountants').mockImplementation(() => {});
      const event = {pageIndex: 0, pageSize: 10} as PageEvent;

      // Act
      component.changePage(event);

      // Assert
      expect(component.loadAccountants).toHaveBeenCalledWith(0, 10);
    })

  describe('renderDeleteConfirmation', () => {
    it('should set selectedAccountId and showDeleteConfirmation', () => {
      // Arrange
      const accountantID = 1;

      // Act
      component.renderDeleteConfirmation(accountantID);

      // Assert
      expect(component.selectedAccountantId).toEqual(accountantID);
      expect(component.showDeleteConfirmation).toBeTruthy();
    })
  })

  describe('filterByName', () => {
    it('should set pageIndex to 0 and call loadAccountants', () => {
      // Arrange
      jest.spyOn(component, 'loadAccountants').mockImplementation(() => {});
      component.paginator = {pageIndex: 1} as any;

      // Act
      component.filterByName();

      // Assert
      expect(component.paginator.pageIndex).toEqual(0);
      expect(component.loadAccountants).toHaveBeenCalledWith(0, 10);
      })
    })
  })

  describe('onDeleteConfirm', () => {
    it ('should call delete with selectedAccountantID and loadAccountants when confirmation is true', () => {
      // Arrange
      jest.spyOn(mockService, 'delete').mockReturnValue(of(null) as any);
      jest.spyOn(component, 'loadAccountants').mockImplementation(() => {});
      component.selectedAccountantId = 1;
  
      // Act
      component.onDeleteConfirm(true);

      // Assert
      expect(mockService.delete).toHaveBeenCalledWith(1);
      expect(component.loadAccountants).toHaveBeenCalledWith(0, 10);
      expect(component.showDeleteConfirmation).toBeFalsy();
    })


    it('should set errorMessage to contador não pode ser excluído when delete returns an error', () => {
      // Arrange
      const errorResponse = new HttpErrorResponse({
        status: 409,
        statusText: 'Esse contador não pode ser excluído pois está vinculado a um cliente'
      });
      jest.spyOn(mockService, 'delete').mockReturnValue(throwError(() => (errorResponse)));

      // Act
      component.onDeleteConfirm(true);

      // Assert
      expect(component.errorMessage).toBe("Esse contador não pode ser excluído pois está vinculado a um cliente");
    });

    it('should set errorMessage to "Erro inesperado" when delete returns an unexpected error', () => {
      // Arrange
      const errorResponse = new HttpErrorResponse({
        status: 500,
        statusText: 'Internal Server Error'
      });
      jest.spyOn(mockService, 'delete').mockReturnValue(throwError(() => (errorResponse)));

      // Act
      component.onDeleteConfirm(true);

      // Assert
      expect(component.errorMessage).toBe("Erro inesperado");
    });

    it('should not call delete, and set errorMessage to empty when confirmation is false', () => {
      // Arrange
      jest.spyOn(mockService, 'delete').mockReturnValue(of(null) as any);
      component.errorMessage = "test";
   
      // Act
      component.onDeleteConfirm(false);

      // Assert
      expect(mockService.delete).not.toHaveBeenCalled();
      expect(component.showDeleteConfirmation).toBeFalsy();
      expect(component.errorMessage).toBe("");
    })
  })
});
