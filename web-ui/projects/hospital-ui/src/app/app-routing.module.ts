import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./modules/csr/csr.module').then(m => m.CsrModule),
  },

  {
    path: 'service-log',
    loadChildren: () => import('./modules/service-logs/service-log.module').then(m => m.ServiceLogModule)
  },
  {
    path: 'service-log-alarm',
    loadChildren: () => import('./modules/service-log-alarm/service-log-alarm.module').then(m => m.ServiceLogAlarmModule)
  },
  {
    path: 'medical-record-log',
    loadChildren: () => import('./modules/medical-record-logs/medical-records-logs.module').then(m => m.MedicalRecordLogsModule)
  },
  {
    path: 'medical-record-alarms',
    loadChildren: () => import('./modules/medical-record-alarm/medical-record-alarm.module').then(m => m.MedicalRecordAlarmModule)
  },
  {
    path: 'rule',
    loadChildren: () => import('./modules/rules/rule.module').then(m => m.RuleModule)
  },
  {
    path: 'patients',
    loadChildren: () => import('./modules/patients/patients.module').then(m => m.PatientsModule)
  },
  {
    path: 'certificates',
    loadChildren: () => import('./modules/certificates/certificates.module').then(m => m.CertificatesModule)
  },
  {
    path: 'logs',
    loadChildren: () => import('./modules/logs/logs.module').then(m => m.LogsModule)
  },
  {
    path: 'reports',
    loadChildren: () => import('./modules/reports/reports.module').then(m => m.ReportsModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
