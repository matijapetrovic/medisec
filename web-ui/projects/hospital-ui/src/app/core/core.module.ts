import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { MenubarModule } from 'primeng/menubar';


@NgModule({
  declarations: [],
  imports: [
    RouterModule,
    CommonModule,
    MenubarModule
  ],
  providers: [
  ],
  exports: [],
})

export class CoreModule { }
