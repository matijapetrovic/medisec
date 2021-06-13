import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from '../../core/core.module';
import { VendorsModule } from 'projects/vendors/src/public-api';

import { ServiceLogALarmRoutingModule } from './service-log-alarm-routing.module';
import { ServiceLogAlarmsComponent } from './service-log-alarms/service-log-alarms.component';

@NgModule({
  declarations: [ServiceLogAlarmsComponent],
  imports: [
    CommonModule,
    VendorsModule,
    ServiceLogALarmRoutingModule,
    CoreModule,
    ReactiveFormsModule
  ]
})
export class ServiceLogAlarmModule { }