import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../../core/guards/auth.guard';
import { LogSourcesListComponent } from './components/log-sources-list/log-sources-list.component';


const routes: Routes = [
  {
    path: 'sources',
    component: LogSourcesListComponent,
    canActivate: [AuthGuard],
    data: { roles: ['admin', 'super-admin']}
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LogsRoutingModule { }
