import { Component, OnInit } from '@angular/core';
import { ServiceLogAlarmService } from '../service-log-alarm.service';
import { ServiceLogAlarm } from '../ServiceLogAlarm';

@Component({
  selector: 'app-service-log-alarms',
  templateUrl: './service-log-alarms.component.html',
  styleUrls: ['./service-log-alarms.component.scss']
})
export class ServiceLogAlarmsComponent implements OnInit {
  serviceLogAlarms: ServiceLogAlarm[];

  constructor(private serviceLogAlarmService: ServiceLogAlarmService) { }

  ngOnInit(): void {
    this.findAll();
  }

  findAll(): void {
    this.serviceLogAlarmService.findAll().subscribe((serviceLogAlarms) => this.serviceLogAlarms = serviceLogAlarms);
  }

}
