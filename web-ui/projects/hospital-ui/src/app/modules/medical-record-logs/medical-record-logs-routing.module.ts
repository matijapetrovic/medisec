import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../../core/guards/auth.guard';
import { MedicalRecordLogsComponent } from './pages/medical-record-logs/medical-record-logs.component';


const routes: Routes = [
  {
    path: '',
    component: MedicalRecordLogsComponent,
    canActivate: [AuthGuard],
    data: { roles: ['doctor']}
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MedicalRecordLogsRoutingModule { }
