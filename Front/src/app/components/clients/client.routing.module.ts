import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListClientsComponent } from './list-clients/list-clients.component';
import { CreateClientComponent } from './create-client/create-client.component';
import { EditClientComponent } from './edit-client/edit-client.component';



const routes: Routes = [
  {
    path: 'listClients',
    component: ListClientsComponent
  },
  {
    path: 'createClient',
    component: CreateClientComponent
  },
  {
    path: 'editClient/:id',
    component: EditClientComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientRoutingModule { }
