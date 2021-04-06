import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CommonModule } from '@angular/common';

import { FlexLayoutModule } from '@angular/flex-layout';

import { ButtonModule } from 'primeng/button';
import { CarouselModule } from 'primeng/carousel';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ConfirmationService } from 'primeng/api';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { CardModule } from 'primeng/card';
import { GMapModule } from 'primeng/gmap';
import { RatingModule } from 'primeng/rating';
import { DropdownModule } from 'primeng/dropdown';
import { TableModule } from 'primeng/table';
import { DynamicDialogModule } from 'primeng/dynamicdialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { InputTextModule } from 'primeng/inputtext';
import { DataViewModule } from 'primeng/dataview';
import { NgSelectModule } from '@ng-select/ng-select';
import { AccordionModule } from 'primeng/accordion';

@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    ButtonModule,
    CarouselModule,
    ConfirmPopupModule,
    ScrollPanelModule,
    CardModule,
    GMapModule,
    RatingModule,
    DropdownModule,
    TableModule,
    DynamicDialogModule,
    ConfirmDialogModule,
    InputTextModule,
    DataViewModule,
    NgSelectModule,
    AccordionModule
  ],
  exports: [
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    ButtonModule,
    CarouselModule,
    ConfirmPopupModule,
    ScrollPanelModule,
    CardModule,
    GMapModule,
    RatingModule,
    DropdownModule,
    TableModule,
    DynamicDialogModule,
    ConfirmDialogModule,
    InputTextModule,
    DataViewModule,
    NgSelectModule,
    AccordionModule
  ],
  providers: [
    ConfirmationService
  ]
})
export class SharedModule { }