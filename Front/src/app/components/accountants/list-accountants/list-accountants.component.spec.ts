import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { ListAccountantsComponent } from './list-accountants.component';
import { Subscription, of } from 'rxjs';



describe('ListAccountantsComponent', () => {
  let component: ListAccountantsComponent;
  let fixture: ComponentFixture<ListAccountantsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListAccountantsComponent ],
      imports: [HttpClientTestingModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListAccountantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should call loadAccountants and subscribe to onKeyDown', () => {
      // Arrange
      jest.spyOn(component, 'loadAccountants').mockImplementation(() => {});
      jest.spyOn(component.onKeyDown, 'subscribe').mockImplementation( () => {
        return of().subscribe();
      });

      const pageIndex = component.pageIndex;
      const pageSize = component.pageSize;
      
      // Act
      component.ngOnInit();

      // Assert
      expect(component.loadAccountants).toHaveBeenCalledWith(pageIndex, pageSize);
      expect(component.onKeyDown.subscribe).toHaveBeenCalled();
    })
  })

  describe('changePage', () => {
    it ('should call loadAccountants', () => {
      // Arrange
      jest.spyOn(component, 'loadAccountants').mockImplementation(() => {});
      const event = {pageIndex: 0, pageSize: 10} as any;

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
        expect(component.selectedAccountId).toEqual(accountantID);
        expect(component.showDeleteConfirmation).toBeTruthy();
      })
    })

    describe('filterByName', () => {
      it('should set pageIndex to 0 and call loadAccountants', () => {
        // Arrange
        jest.spyOn(component, 'loadAccountants').mockImplementation(() => {});
        const filter = {pageIndex: 1, pageSize: 10} as any;
        component.paginator = {pageIndex: 1} as any;

        // Act
        component.filterByName();

        // Assert
        expect(component.paginator.pageIndex).toEqual(0);
        expect(component.loadAccountants).toHaveBeenCalledWith(0, 10);
      })
    })
  })

  // describe('loadAccountants', () => {
  //   it('should call service.listAccountantsData', () => {
  //     // Arrange
  //     jest.spyOn(component.service, 'listAccountantsData').mockImplementation(() => of());
  //     const pageIndex = 0;
  //     const pageSize = 10;

  //     // Act
  //     component.loadAccountants(pageIndex, pageSize);

  //     // Assert
  //     expect(component.service.listAccountantsData).toHaveBeenCalledWith('', pageIndex, pageSize);
  //   })
  // })

});
