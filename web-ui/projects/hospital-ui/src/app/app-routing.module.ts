import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./modules/csr/csr.module').then(m => m.CsrModule),
  },
  {
    path: 'medical-record-log',
    loadChildren: () => import('./modules/medical-record-logs/medical-records-logs.module').then(m => m.MedicalRecordLogsModule)
  },
  {
    path: 'service-log',
    loadChildren: () => import('./modules/service-logs/service-log.module').then(m => m.ServiceLogModule)
  },
  {
    path: 'service-log-alarm',
    loadChildren: () => import('./modules/service-log-alarm/service-log-alarm.module').then(m => m.ServiceLogAlarmModule)
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
