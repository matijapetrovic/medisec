import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientsListComponent } from './components/patients-list/patients-list.component';
import { PatientsRoutingModule } from './patients-routing.module';
import { VendorsModule } from 'projects/vendors/src/public-api';
import { PatientDetailsComponent } from './components/patient-details/patient-details.component';



@NgModule({
  declarations: [PatientsListComponent, PatientDetailsComponent],
  imports: [
    CommonModule,
    VendorsModule,
    PatientsRoutingModule
  ]
})
export class PatientsModule { }
