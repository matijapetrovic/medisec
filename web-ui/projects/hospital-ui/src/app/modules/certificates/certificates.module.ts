import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from '../../core/core.module';
import { VendorsModule } from 'projects/vendors/src/public-api';
import { CertificatesRoutingModule } from './certificates-routing.module';
import { CertificatesComponent } from './pages/certificates/certificates.component';

@NgModule({
  declarations: [CertificatesComponent],
  imports: [
    CommonModule,
    VendorsModule,
    CertificatesRoutingModule,
    CoreModule,
    ReactiveFormsModule
  ]
})
export class CertificatesModule { }
