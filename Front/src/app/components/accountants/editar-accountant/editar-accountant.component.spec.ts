import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarAccountantComponent } from './editar-accountant.component';

describe('EditarAccountantComponent', () => {
  let component: EditarAccountantComponent;
  let fixture: ComponentFixture<EditarAccountantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditarAccountantComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditarAccountantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
