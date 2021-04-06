import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { MenubarModule } from 'primeng/menubar';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [],
  imports: [
    RouterModule,
    CommonModule,
    MenubarModule,
    SharedModule
  ],
  providers: [ 
  ],
  exports: [],
})

export class CoreModule { }