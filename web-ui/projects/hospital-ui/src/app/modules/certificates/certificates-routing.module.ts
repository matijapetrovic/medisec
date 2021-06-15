import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../../core/guards/auth.guard';
import { CertificatesComponent } from './pages/certificates/certificates.component';


const routes: Routes = [
  {
    path: '',
    component: CertificatesComponent,
    canActivate: [AuthGuard],
    data: { roles: ['admin']}
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CertificatesRoutingModule { }
