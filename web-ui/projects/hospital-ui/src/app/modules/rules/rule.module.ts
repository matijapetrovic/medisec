import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from '../../core/core.module';
import { VendorsModule } from 'projects/vendors/src/public-api';
import { AddRuleComponent } from './components/add-rule/add-rule.component';
import { RuleRoutingModule } from './rule-routing.module';
import { AceEditorModule } from 'ng2-ace-editor';

@NgModule({
  declarations: [AddRuleComponent],
  imports: [
    CommonModule,
    VendorsModule,
    RuleRoutingModule,
    CoreModule,
    ReactiveFormsModule,
    AceEditorModule
  ]
})
export class RuleModule { }
