import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CertificateRequestsComponent } from './pages/certificate-requests/certificate-requests.component';
import { IssueCertitifcatesComponent } from './pages/issue-certitifcates/issue-certitifcates.component';

const routes: Routes = [
  {
    path:'requests',
    component: CertificateRequestsComponent
  },
  {
    path: 'requests/:csrId/issue',
    component: IssueCertitifcatesComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CertificatesRoutingModule { }
