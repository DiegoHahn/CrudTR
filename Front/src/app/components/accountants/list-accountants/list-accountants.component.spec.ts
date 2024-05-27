import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { ListAccountantsComponent } from './list-accountants.component';

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
});
