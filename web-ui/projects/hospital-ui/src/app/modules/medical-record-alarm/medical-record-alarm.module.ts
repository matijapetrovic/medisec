import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from '../../core/core.module';
import { VendorsModule } from 'projects/vendors/src/public-api';
import { MedicalRecordAlarmsComponent } from './pages/medical-record-alarms/medical-record-alarms.component';
import { MedicalRecordAlarmRoutingModule } from './medical-record-alarm-routing.module';

@NgModule({
  declarations: [MedicalRecordAlarmsComponent],
  imports: [
    CommonModule,
    VendorsModule,
    MedicalRecordAlarmRoutingModule,
    CoreModule,
    ReactiveFormsModule
  ]
})
export class MedicalRecordAlarmModule { }
