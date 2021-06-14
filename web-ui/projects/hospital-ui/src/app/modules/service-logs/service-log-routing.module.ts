import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ServiceLogSearchComponent } from './service-log-search/service-log-search.component';


const routes: Routes = [
  {
    path: 'search',
    component: ServiceLogSearchComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ServiceLogRoutingModule { }