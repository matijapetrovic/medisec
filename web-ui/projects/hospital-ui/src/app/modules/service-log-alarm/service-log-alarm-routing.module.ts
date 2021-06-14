import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../../core/guards/auth.guard';
import { ServiceLogAlarmsComponent } from './service-log-alarms/service-log-alarms.component';


const routes: Routes = [
  {
    path: '',
    component: ServiceLogAlarmsComponent,
    canActivate: [AuthGuard],
    data: { roles: ['admin']}
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ServiceLogALarmRoutingModule { }
