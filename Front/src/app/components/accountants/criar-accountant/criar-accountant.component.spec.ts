import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarAccountantComponent } from './criar-accountant.component';

describe('CriarAccountantComponent', () => {
  let component: CriarAccountantComponent;
  let fixture: ComponentFixture<CriarAccountantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CriarAccountantComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CriarAccountantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
