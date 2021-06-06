import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MedicalRecordLogsComponent } from './pages/medical-record-logs/medical-record-logs.component';


const routes: Routes = [
  {
    path: '',
    component: MedicalRecordLogsComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MedicalRecordLogsRoutingModule { }