import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAccountantComponent } from './create-accountant.component';

describe('CreateAccountantComponent', () => {
  let component: CreateAccountantComponent;
  let fixture: ComponentFixture<CreateAccountantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateAccountantComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateAccountantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
