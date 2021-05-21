import { NgModule } from '@angular/core';
import { AccordionModule } from 'primeng/accordion';

import {ButtonModule} from 'primeng/button';
import { CardModule } from 'primeng/card';
import { CarouselModule } from 'primeng/carousel';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { DataViewModule } from 'primeng/dataview';
import { DropdownModule } from 'primeng/dropdown';
import { DynamicDialogModule } from 'primeng/dynamicdialog';
import { GMapModule } from 'primeng/gmap';
import { InputTextModule } from 'primeng/inputtext';
import { RatingModule } from 'primeng/rating';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { TableModule } from 'primeng/table';
import {CalendarModule} from 'primeng/calendar';
import {TriStateCheckboxModule} from 'primeng/tristatecheckbox';
import { MenubarModule } from 'primeng/menubar';
import {CheckboxModule} from 'primeng/checkbox';
import {InputNumberModule} from 'primeng/inputnumber';
@NgModule({
  declarations: [],
  imports: [
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
    AccordionModule,
    CalendarModule,
    TriStateCheckboxModule,
    MenubarModule,
    CheckboxModule,
    InputNumberModule
  ],
  exports: [
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
    AccordionModule,
    CalendarModule,
    TriStateCheckboxModule,
    MenubarModule,
    CheckboxModule,
    InputNumberModule
  ]
})
export class PrimengModule { }
