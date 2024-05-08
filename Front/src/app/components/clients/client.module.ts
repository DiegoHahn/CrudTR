import { CommonModule, DatePipe } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule, MatSelectModule } from '@angular/material';
import { MAT_DATE_LOCALE, MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectInfiniteScrollModule } from 'ng-mat-select-infinite-scroll';
import { NgxMaskModule } from 'ngx-mask';
import { ClientRoutingModule } from './client.routing.module';
import { ClientComponent } from './client/client.component';
import { ClientService } from './clients.service';
import { CreateClientComponent } from './create-client/create-client.component';
import { DeleteClientComponent } from './delete-client/delete-client.component';
import { ListClientsComponent } from './list-clients/list-clients.component';


@NgModule({
  declarations: [
    ClientComponent,
    ListClientsComponent,
    DeleteClientComponent,
    CreateClientComponent,
  ],
  imports: [
    CommonModule,
    ClientRoutingModule,
    FormsModule,
    MatDatepickerModule ,
    MatFormFieldModule,
    MatSelectInfiniteScrollModule,
    MatNativeDateModule,
    MatSelectModule,
    NgxMaskModule.forRoot(),
    HttpClientModule,
    ReactiveFormsModule,    
    MatPaginatorModule
  ],
  providers:[
    ClientService, 
    DatePipe,
    { provide: MAT_DATE_LOCALE, useValue: 'pt-BR' },]
})
export class ClientModule { }

