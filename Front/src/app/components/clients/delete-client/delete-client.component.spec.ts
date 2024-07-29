import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteClientComponent } from './delete-client.component';

describe('DeleteClientComponent', () => {
  let component: DeleteClientComponent;
  let fixture: ComponentFixture<DeleteClientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteClientComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeleteClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('confirmDeletion() should emit true', () => {
    // Arrange
    jest.spyOn(component.confirmation, 'emit');

    // Act
    component.confirmDeletion();

    // Assert
    expect(component.confirmation.emit).toHaveBeenCalledWith(true);
  });

  it('cancelDeletion() should emit false', () => {
    // Arrange
    jest.spyOn(component.confirmation, 'emit');

    // Act
    component.cancelDeletion();

    // Assert
    expect(component.confirmation.emit).toHaveBeenCalledWith(false);
  });
});
