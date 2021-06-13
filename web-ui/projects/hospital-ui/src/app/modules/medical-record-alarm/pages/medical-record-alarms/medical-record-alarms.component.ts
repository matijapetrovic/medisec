import { Component, OnInit } from '@angular/core';
import { MedicalRecordAlarm } from '../../MedicalRecordAlarm';
import { MedicalRecordAlarmService } from '../../medical-record-alarm.service'

@Component({
  selector: 'app-medical-record-alarms',
  templateUrl: './medical-record-alarms.component.html',
  styleUrls: ['./medical-record-alarms.component.scss']
})
export class MedicalRecordAlarmsComponent implements OnInit {

  medicalRecordAlarms: MedicalRecordAlarm[];

  constructor(
    private service: MedicalRecordAlarmService
  ) { }

  ngOnInit(): void {
    this.service.getAll().subscribe((data) => this.medicalRecordAlarms = data);
  }

}
