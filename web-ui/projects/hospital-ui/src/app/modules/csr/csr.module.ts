import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SendCsrFormComponent } from './pages/send-csr-form/send-csr-form.component';

import { FlexLayoutModule } from '@angular/flex-layout';

import { SharedModule } from './../../shared/shared.module';
import { CsrRoutingModule } from './csr-routing.module';
import { CoreModule } from 'src/app/core/core.module';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [SendCsrFormComponent],
  imports: [
    CommonModule,
    CsrRoutingModule,
    SharedModule,
    FlexLayoutModule,
    CoreModule,
    ReactiveFormsModule
  ]
})
export class CsrModule { }
