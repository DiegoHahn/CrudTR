import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatPaginatorModule } from '@angular/material/paginator';
import { BrowserModule } from '@angular/platform-browser';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { NgxMaskModule } from 'ngx-mask';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AccountantModule } from './components/accountants/accountant.module';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AccountantModule,
    AppRoutingModule,
    FormsModule,
    NgxMaskModule.forRoot(),
    HttpClientModule,
    ReactiveFormsModule,
    MatPaginatorModule,
    NoopAnimationsModule
  ],
  providers:[],
  bootstrap: [AppComponent]
})
export class AppModule { }

