import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./modules/csr/csr.module').then(m => m.CsrModule),
  },
  {
    path: 'medical-record-logs',
    loadChildren: () => import('./modules/medical-record-logs/medical-records-logs.module').then(m => m.MedicalRecordLogsModule)
  },
  {
    path: 'patients',
    loadChildren: () => import('./modules/patients/patients.module').then(m => m.PatientsModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
