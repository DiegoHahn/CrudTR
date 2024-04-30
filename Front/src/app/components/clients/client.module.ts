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

@NgModule({
  declarations: [
    ClientComponent,
    ListClientsComponent,
    DeleteClientComponent
  ],
  imports: [
    CommonModule,
    ClientRoutingModule,
    FormsModule,
    NgxMaskModule.forRoot(),
    HttpClientModule,
    ReactiveFormsModule,    
    MatPaginatorModule
  ],
  providers:[ClientService]
})
export class ClientModule { }

