import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatPaginatorModule } from '@angular/material/paginator';
import { NgxMaskModule } from 'ngx-mask';
import { AccountantRoutingModule } from './accountant.rounting.module';
import { AccountantComponent } from './accountant/accountant.component';
import { AccountantService } from './accountants.service';
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
    CommonModule,
    AccountantRoutingModule,
    FormsModule,
    NgxMaskModule.forRoot(),
    HttpClientModule,
    ReactiveFormsModule,
    MatPaginatorModule,

  ],
  providers:[AccountantService]
})
export class AccountantModule { }

