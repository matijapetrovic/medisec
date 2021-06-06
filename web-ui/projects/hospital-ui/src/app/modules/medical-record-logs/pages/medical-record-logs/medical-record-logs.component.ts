import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MedicalRecordLogsService } from '../../medical-record-logs.service';
import { MedicalRecordLog } from '../../MedicalRecordLog';

@Component({
  selector: 'app-medical-record-logs',
  templateUrl: './medical-record-logs.component.html',
  styleUrls: ['./medical-record-logs.component.scss']
})
export class MedicalRecordLogsComponent implements OnInit {

  medicalRecordLogs: MedicalRecordLog[];

  constructor(
    private medicalRecordService: MedicalRecordLogsService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.medicalRecordService.getAll().subscribe((medicalRecordLogs) => this.medicalRecordLogs = medicalRecordLogs);
  }
}
