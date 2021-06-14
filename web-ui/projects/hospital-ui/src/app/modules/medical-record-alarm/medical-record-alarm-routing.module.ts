import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../../core/guards/auth.guard';
import { MedicalRecordAlarmsComponent } from './pages/medical-record-alarms/medical-record-alarms.component';


const routes: Routes = [
  {
    path: '',
    component: MedicalRecordAlarmsComponent,
    canActivate: [AuthGuard],
    data: { roles: ['doctor']}
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MedicalRecordAlarmRoutingModule { }
