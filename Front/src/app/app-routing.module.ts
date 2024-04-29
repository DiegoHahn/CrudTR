import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


const routes: Routes = [
  {
    path: 'accountants',
    loadChildren: () => import('./components/accountants/accountant.module').then(m => m.AccountantModule)
  },
  {
    path: 'clients',
    loadChildren: () => import('./components/clients/client.module').then(m => m.ClientModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
