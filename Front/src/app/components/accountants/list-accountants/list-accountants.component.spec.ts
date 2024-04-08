import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAccountantsComponent } from './list-accountants.component';

describe('ListAccountantsComponent', () => {
  let component: ListAccountantsComponent;
  let fixture: ComponentFixture<ListAccountantsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListAccountantsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListAccountantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
