import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';

import { HttpClientTestingModule } from '@angular/common/http/testing';
import { PageEvent } from '@angular/material';
import { of, throwError } from 'rxjs';
import { ClientService } from '../clients.service';
import { ListClientsComponent } from './list-clients.component';

describe('ListClientsComponent', () => {
  let component: ListClientsComponent;
  let fixture: ComponentFixture<ListClientsComponent>;
  let mockService: ClientService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListClientsComponent ],
      imports: [HttpClientTestingModule],
      providers: [ClientService]
      
    })
    .compileComponents();
  });

    beforeEach(() => {
    mockService = TestBed.inject(ClientService);
    fixture = TestBed.createComponent(ListClientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    })

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should call loadClients, subscribe to onKeyDown and call filterByName after debounce time', fakeAsync(() => {
      // Arrange
      jest.spyOn(component, 'loadClients').mockImplementation(() => {});
      jest.spyOn(component, 'filterByName').mockImplementation(() => {});
      const mockKeyboardEvent = new KeyboardEvent("keydown", {
        key: "Enter"
      });
      const pageIndex = component.pageIndex;
      const pageSize = component.pageSize;
      
      // Act
      component.ngOnInit();
      component.onKeyDown.next(mockKeyboardEvent);
      tick(600);
  
      // Assert
      expect(component.loadClients).toHaveBeenCalledWith(pageIndex, pageSize);
      expect(component.filterByName).toHaveBeenCalled();
    }));
  });

  describe('loadClients', () => {
    it('should call listClientsData and set listClients and totalElements', () => {
      // Arrange
      const response: any = {
        content: ['client1', 'client2'],
        totalElements: 2
      };
      jest.spyOn(mockService, 'listClientsData').mockReturnValue(of(response));
      const pageIndex = 0;
      const pageSize = 10;
    
      // Act
      component.loadClients(pageIndex, pageSize);
    
      // Assert
      expect(mockService.listClientsData).toHaveBeenCalledWith(component.nameFilter, pageIndex, pageSize);
      expect(component.listClients).toEqual(response.content);
      expect(component.totalElements).toEqual(response.totalElements);
    });
  });

  describe('changePage', () => {
    it ('should call loadClients', () => {
      // Arrange
      jest.spyOn(component, 'loadClients').mockImplementation(() => {});
      const event = {pageIndex: 0, pageSize: 10} as PageEvent;

      // Act
      component.changePage(event);

      // Assert
      expect(component.loadClients).toHaveBeenCalledWith(event.pageIndex, event.pageSize);
    });
  });

  describe('renderDeleteConfirmation', () => {
    it('should set selectedClient and showDeleteConfirmation', () => {
      // Arrange
      const clientID = 1;

      // Act
      component.renderDeleteConfirmation(clientID);

      // Assert
      expect(component.selectedClientId).toEqual(clientID);
      expect(component.showDeleteConfirmation).toBeTruthy();
    })
  })

  describe('filterByName', () => {
    it('should set pageIndex to 0 and call loadClients', () => {
      // Arrange
      jest.spyOn(component, 'loadClients').mockImplementation(() => {});
      component.paginator = {pageIndex: 1} as any;

      // Act
      component.filterByName();

      // Assert
      expect(component.paginator.pageIndex).toEqual(0);
      expect(component.loadClients).toHaveBeenCalledWith(0, 10);
    })
  })

  describe('onDeleteConfirm', () => {
    it ('should call delete with selectedClientID and loadClients when confirmation is true', () => {
      // Arrange
      jest.spyOn(mockService, 'delete').mockReturnValue(of(null) as any);
      jest.spyOn(component, 'loadClients').mockImplementation(() => {});
      component.selectedClientId = 1;

      // Act
      component.onDeleteConfirm(true);

      // Assert
      expect(mockService.delete).toHaveBeenCalledWith(1);
      expect(component.loadClients).toHaveBeenCalledWith(0, 10);
      expect(component.showDeleteConfirmation).toBeFalsy();
    });

    it ('should log the error message when delete fails', () => {
      // Arrange
      const error = new Error('Error message');
      jest.spyOn(component, 'loadClients').mockImplementation(() => {});
      jest.spyOn(mockService, 'delete').mockReturnValue(throwError(() => (error)));
      jest.spyOn(console, 'log');

      component.selectedClientId = 1;

      // Act
      component.onDeleteConfirm(true);

      // Assert
      expect(console.log).toHaveBeenCalledWith(error);
    });

    it ('should not call delete when confirmation is false', () => {
      // Arrange
      jest.spyOn(mockService, 'delete').mockReturnValue(of(null) as any);
      jest.spyOn(component, 'loadClients').mockImplementation(() => {});
      component.selectedClientId = 1;

      // Act
      component.onDeleteConfirm(false);

      // Assert
      expect(mockService.delete).not.toHaveBeenCalled();
      expect(component.loadClients).not.toHaveBeenCalled();
      expect(component.showDeleteConfirmation).toBeFalsy();
    });
  });

})



