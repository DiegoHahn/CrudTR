import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateAccountantComponent } from './components/accountants/create-accountant/create-accountant.component';
import { ListAccountantsComponent } from './components/accountants/list-accountants/list-accountants.component';


const routes: Routes = [
  {
    path: '',
    redirectTo: '',
    pathMatch: 'full'
  },
  {
    path:'listAccountants',
    component: ListAccountantsComponent
  },
  {
    path:'createAccountant',
    component: CreateAccountantComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
