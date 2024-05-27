import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        AppComponent,
        HeaderComponent,
        FooterComponent
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the app', () => {
    expect(component).toBeTruthy();
  });

  it(`should have as title 'CrudTR'`, () => {
    expect(component.title).toEqual('CrudTR');
  });

  it('should render header', () => {
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('app-header')).not.toBe(null);
  });

  it('should render footer', () => {
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('app-footer')).not.toBe(null);
  });
});