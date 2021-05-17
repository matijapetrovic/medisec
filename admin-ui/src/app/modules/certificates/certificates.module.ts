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
import { CoreModule } from 'src/app/core/core.module';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { KeycloakBearerInterceptor } from 'keycloak-angular';
import { CertificateService } from './certificate.service';

@NgModule({
  declarations: [IssueCertitifcatesComponent, IssueCertificateFormComponent, CertificateRequestsComponent, CertificatesComponent],
  imports: [
    CommonModule,
    CertificatesRoutingModule,
    InputTextModule,
    ButtonModule,
    TableModule,
    ReactiveFormsModule,
    CoreModule
  ]
})
export class CertificatesModule { }
