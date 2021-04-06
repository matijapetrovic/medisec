import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { RouterModule } from '@angular/router';

import {MenubarModule} from 'primeng/menubar';


@NgModule({
  declarations: [HeaderComponent],
  imports: [
    CommonModule,
    RouterModule,
    MenubarModule,
  ],
  exports: [HeaderComponent]
})
export class CoreModule { }
