import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../../core/guards/auth.guard';
import { PatientsListComponent } from './components/patients-list/patients-list.component';


const routes: Routes = [
  {
    path: '',
    component: PatientsListComponent,
    canActivate: [AuthGuard],
    data: { roles: ['doctor']}
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PatientsRoutingModule { }
