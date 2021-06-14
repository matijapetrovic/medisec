import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from '../../core/core.module';
import { VendorsModule } from 'projects/vendors/src/public-api';

import { ServiceLogRoutingModule } from './service-log-routing.module';
import { ServiceLogSearchComponent } from './service-log-search/service-log-search.component';

@NgModule({
  declarations: [ServiceLogSearchComponent],
  imports: [
    CommonModule,
    VendorsModule,
    ServiceLogRoutingModule,
    CoreModule,
    ReactiveFormsModule
  ]
})
export class ServiceLogModule { }