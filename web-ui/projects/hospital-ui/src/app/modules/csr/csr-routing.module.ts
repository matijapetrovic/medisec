import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SendCsrFormComponent } from './pages/send-csr-form/send-csr-form.component';

const routes: Routes = [
  {
    path: '',
    component: SendCsrFormComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CsrRoutingModule { }