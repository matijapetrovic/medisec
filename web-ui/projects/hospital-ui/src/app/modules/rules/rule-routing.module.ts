import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../../core/guards/auth.guard';
import { AddRuleComponent } from './components/add-rule/add-rule.component';

const routes: Routes = [
  {
    path: 'add-rule',
    component: AddRuleComponent,
    canActivate: [AuthGuard],
    data: { roles: ['doctor', 'admin']}
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RuleRoutingModule { }
