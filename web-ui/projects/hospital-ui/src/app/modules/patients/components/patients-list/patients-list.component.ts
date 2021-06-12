import { Component, OnInit } from '@angular/core';
import { DialogService } from 'primeng/dynamicdialog';
import { Patient } from '../../patient';
import { PatientService } from '../../patient.service';
import { PatientDetailsComponent } from '../patient-details/patient-details.component';

@Component({
  selector: 'app-patients-list',
  templateUrl: './patients-list.component.html',
  styleUrls: ['./patients-list.component.scss'],
  providers: [DialogService]
})
export class PatientsListComponent implements OnInit {
  patients: Patient[];

  constructor(private patientService: PatientService, private dialogService: DialogService) { }

  ngOnInit(): void {
    this.patientService.getPatients().subscribe((patients) => this.patients = patients);
  }

  showMedicalRecord(patient: Patient) {
    const ref = this.dialogService.open(PatientDetailsComponent, {
      data: {
        patient: patient
      },
      header: 'Patient Medical Record',
      width: '50%',
      dismissableMask: true
    });

  }

}
