import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';


import { NgxMaskModule } from 'ngx-mask';
import {MatPaginatorModule} from '@angular/material/paginator'; 
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import { AccountantRoutingModule } from './accountant.rounting.module';
import { HeaderComponent } from '../header/header.component';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from 'src/app/app.component';
import { FooterComponent } from '../footer/footer.component';
import { AccountantComponent } from './accountant/accountant.component';
import { CreateAccountantComponent } from './create-accountant/create-accountant.component';
import { DeleteAccountantComponent } from './delete-accountant/delete-accountant.component';
import { EditAccountantComponent } from './edit-accountant/edit-accountant.component';
import { ListAccountantsComponent } from './list-accountants/list-accountants.component';

@NgModule({
  declarations: [
    AccountantComponent,
    CreateAccountantComponent,
    ListAccountantsComponent,
    EditAccountantComponent,
    DeleteAccountantComponent
  ],
  imports: [
    BrowserModule,
    AccountantRoutingModule,
    FormsModule,
    NgxMaskModule.forRoot(),
    HttpClientModule,
    ReactiveFormsModule,
    MatPaginatorModule,
    NoopAnimationsModule
  ],
  providers:[]
})
export class AccountantModule { }

