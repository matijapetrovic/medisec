import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LogSourcesListComponent } from './components/log-sources-list/log-sources-list.component';
import { VendorsModule } from 'projects/vendors/src/public-api';
import { LogsRoutingModule } from './logs-routing.module';
import { LogSourceFormComponent } from './components/log-source-form/log-source-form.component';



@NgModule({
  declarations: [LogSourcesListComponent, LogSourceFormComponent],
  imports: [
    CommonModule,
    VendorsModule,
    LogsRoutingModule
  ]
})
export class LogsModule { }
