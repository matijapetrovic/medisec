import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IssueCertitifcatesComponent } from './pages/issue-certitifcates/issue-certitifcates.component';
import { IssueCertificateFormComponent } from './components/issue-certificate-form/issue-certificate-form.component';

import {CertificatesRoutingModule} from './certificates-routing.module';
import {InputTextModule} from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';
import { CertificateRequestsComponent } from './pages/certificate-requests/certificate-requests.component';
import {TableModule} from 'primeng/table';
import { CertificatesComponent } from './pages/certificates/certificates.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from '../../core/core.module';
import { VendorsModule } from 'projects/vendors/src/public-api';

@NgModule({
  declarations: [IssueCertitifcatesComponent, IssueCertificateFormComponent, CertificateRequestsComponent, CertificatesComponent],
  imports: [
    CommonModule,
    CertificatesRoutingModule,
    VendorsModule,
    CoreModule
  ]
})
export class CertificatesModule { }
