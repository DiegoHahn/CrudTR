import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { AccountantComponent } from './components/accountants/accountant/accountant.component';
import { CreateAccountantComponent } from './components/accountants/create-accountant/create-accountant.component';
import { BrowserModule } from '@angular/platform-browser';
import { ListAccountantsComponent } from './components/accountants/list-accountants/list-accountants.component';
import { EditAccountantComponent } from './components/accountants/edit-accountant/edit-accountant.component';
import { DeleteAccountantComponent } from './components/accountants/delete-accountant/delete-accountant.component';
import { NgxMaskModule } from 'ngx-mask';
import {MatPaginatorModule} from '@angular/material/paginator'; 
import {NoopAnimationsModule} from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    AccountantComponent,
    CreateAccountantComponent,
    ListAccountantsComponent,
    EditAccountantComponent,
    DeleteAccountantComponent
  ],
  imports: [
    BrowserModule,
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

