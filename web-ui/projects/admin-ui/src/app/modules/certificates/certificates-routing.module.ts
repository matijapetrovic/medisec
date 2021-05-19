import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../../core/guards/auth.guard';
import { CertificateRequestsComponent } from './pages/certificate-requests/certificate-requests.component';
import { CertificatesComponent } from './pages/certificates/certificates.component';
import { IssueCertitifcatesComponent } from './pages/issue-certitifcates/issue-certitifcates.component';

const routes: Routes = [
  {
    path: '',
    component: CertificatesComponent,
    canActivate: [AuthGuard],
    data: { roles: ['super-admin']}
  },
  {
    path:'requests',
    component: CertificateRequestsComponent,
    canActivate: [AuthGuard],
    data: { roles: ['super-admin']}
  },
  {
    path: 'requests/:csrId/issue',
    component: IssueCertitifcatesComponent,
    canActivate: [AuthGuard],
    data: { roles: ['super-admin']}
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CertificatesRoutingModule { }
