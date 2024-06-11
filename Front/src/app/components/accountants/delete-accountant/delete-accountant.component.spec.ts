import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DeleteAccountantComponent } from './delete-accountant.component';

describe('DeleteAccountantComponent', () => {
  let component: DeleteAccountantComponent;
  let fixture: ComponentFixture<DeleteAccountantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteAccountantComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteAccountantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
 
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('confirmDeletion', () => {
    it('should emit true', () => {
      // Arrange
      jest.spyOn(component.confirmation, 'emit');

      // Act
      component.confirmDeletion();

      // Assert
      expect(component.confirmation.emit).toHaveBeenCalledWith(true);
    });
  });

  describe('cancelDeletion', () => {
    it('should emit false', () => {
      // Arrange
      jest.spyOn(component.confirmation, 'emit');

      // Act
      component.cancelDeletion();

      // Assert
      expect(component.confirmation.emit).toHaveBeenCalledWith(false);
    });
  });
});
