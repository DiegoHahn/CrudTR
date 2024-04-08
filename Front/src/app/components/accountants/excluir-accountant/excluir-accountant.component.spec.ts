import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExcluirAccountantComponent } from './excluir-accountant.component';

describe('ExcluirAccountantComponent', () => {
  let component: ExcluirAccountantComponent;
  let fixture: ComponentFixture<ExcluirAccountantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExcluirAccountantComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExcluirAccountantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
