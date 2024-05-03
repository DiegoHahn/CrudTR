import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatPaginatorModule } from '@angular/material/paginator';
import { NgxMaskModule } from 'ngx-mask';
import { ClientComponent } from './client/client.component';
import { ClientRoutingModule } from './client.routing.module';
import { ClientService } from './clients.service';
import { ListClientsComponent } from './list-clients/list-clients.component';
import { DeleteClientComponent } from './delete-client/delete-client.component';
import { CreateClientComponent } from './create-client/create-client.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MAT_DATE_LOCALE, MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material';

@NgModule({
  declarations: [
    ClientComponent,
    ListClientsComponent,
    DeleteClientComponent,
    CreateClientComponent
  ],
  imports: [
    CommonModule,
    ClientRoutingModule,
    FormsModule,
    MatDatepickerModule ,
    MatFormFieldModule,
    MatNativeDateModule,
    NgxMaskModule.forRoot(),
    HttpClientModule,
    ReactiveFormsModule,    
    MatPaginatorModule
  ],
  providers:[ClientService, { provide: MAT_DATE_LOCALE, useValue: 'pt-BR' }]
})
export class ClientModule { }

