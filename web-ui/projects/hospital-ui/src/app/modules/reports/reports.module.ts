import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from '../../core/core.module';
import { VendorsModule } from 'projects/vendors/src/public-api';
import { AceEditorModule } from 'ng2-ace-editor';
import { ReportsRoutingModule } from './reports-routing.module';
import { ReportsComponent } from './pages/reports/reports.component';
import {ChartModule} from 'primeng/chart';

@NgModule({
  declarations: [ReportsComponent],
  imports: [
    CommonModule,
    VendorsModule,
    ReportsRoutingModule,
    CoreModule,
    ReactiveFormsModule,
    AceEditorModule,
    ChartModule
  ]
})
export class ReportsModule { }
