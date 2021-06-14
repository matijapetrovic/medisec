import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MedicalRecordAlarmsComponent } from './pages/medical-record-alarms/medical-record-alarms.component';


const routes: Routes = [
  {
    path: '',
    component: MedicalRecordAlarmsComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MedicalRecordAlarmRoutingModule { }
