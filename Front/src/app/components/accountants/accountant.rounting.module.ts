import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateAccountantComponent } from './create-accountant/create-accountant.component';
import { EditAccountantComponent } from './edit-accountant/edit-accountant.component';
import { ListAccountantsComponent } from './list-accountants/list-accountants.component';


const routes: Routes = [
  {
    path:'list',
    component: ListAccountantsComponent
  },
  {
    path:'createAccountant',
    component: CreateAccountantComponent
  },
  {
    path: 'editAccountant/:id',
    component: EditAccountantComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccountantRoutingModule { }
