import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SendCsrFormComponent } from './pages/send-csr-form/send-csr-form.component';

import { CsrRoutingModule } from './csr-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from '../../core/core.module';
import { VendorsModule } from 'projects/vendors/src/public-api';

@NgModule({
  declarations: [SendCsrFormComponent],
  imports: [
    CommonModule,
    VendorsModule,
    CsrRoutingModule,
    CoreModule,
    ReactiveFormsModule
  ]
})
export class CsrModule { }
