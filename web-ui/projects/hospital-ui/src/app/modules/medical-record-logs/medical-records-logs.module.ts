import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from '../../core/core.module';
import { VendorsModule } from 'projects/vendors/src/public-api';
import { MedicalRecordLogsComponent } from './pages/medical-record-logs/medical-record-logs.component';
import { MedicalRecordLogsRoutingModule } from './medical-record-logs-routing.module';

@NgModule({
  declarations: [MedicalRecordLogsComponent],
  imports: [
    CommonModule,
    VendorsModule,
    MedicalRecordLogsRoutingModule,
    CoreModule,
    ReactiveFormsModule
  ]
})
export class MedicalRecordLogsModule { }