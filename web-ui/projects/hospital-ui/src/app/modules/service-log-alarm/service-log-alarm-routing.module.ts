import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ServiceLogAlarmsComponent } from './service-log-alarms/service-log-alarms.component';


const routes: Routes = [
  {
    path: '',
    component: ServiceLogAlarmsComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ServiceLogALarmRoutingModule { }