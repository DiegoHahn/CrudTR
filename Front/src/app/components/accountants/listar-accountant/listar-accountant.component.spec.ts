import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarAccountantComponent } from './listar-accountant.component';

describe('ListarAccountantComponent', () => {
  let component: ListarAccountantComponent;
  let fixture: ComponentFixture<ListarAccountantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListarAccountantComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListarAccountantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
