import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../../core/guards/auth.guard';

import { SendCsrFormComponent } from './pages/send-csr-form/send-csr-form.component';

const routes: Routes = [
  {
    path: '',
    component: SendCsrFormComponent,
    canActivate: [AuthGuard],
    data: { roles: ['admin']}
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CsrRoutingModule { }
