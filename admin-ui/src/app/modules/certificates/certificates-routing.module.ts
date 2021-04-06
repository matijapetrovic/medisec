import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CertificateRequestsComponent } from './pages/certificate-requests/certificate-requests.component';
import { CertificatesComponent } from './pages/certificates/certificates.component';
import { IssueCertitifcatesComponent } from './pages/issue-certitifcates/issue-certitifcates.component';

const routes: Routes = [
  {
    path: '',
    component: CertificatesComponent
  },
  {
    path:'requests',
    component: CertificateRequestsComponent
  },
  {
    path: 'requests/:csrId/issue',
    component: IssueCertitifcatesComponent
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CertificatesRoutingModule { }
