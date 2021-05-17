import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PrimengModule } from './primeng/primeng.module';



@NgModule({
  declarations: [],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    PrimengModule
  ],
  exports: [
    FormsModule,
    ReactiveFormsModule,
    PrimengModule
  ]
})
export class VendorsModule { }
