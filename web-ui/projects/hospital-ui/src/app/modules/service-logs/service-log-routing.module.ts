import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../../core/guards/auth.guard';
import { ServiceLogSearchComponent } from './service-log-search/service-log-search.component';


const routes: Routes = [
  {
    path: 'search',
    component: ServiceLogSearchComponent,
    canActivate: [AuthGuard],
    data: { roles: ['admin']}
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ServiceLogRoutingModule { }
