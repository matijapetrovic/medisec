import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServiceLogService } from '../service-logs.service';
import { ServiceLog } from '../ServiceLog';
import { InputTextModule } from 'primeng/inputtext'
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-service-log-search',
  templateUrl: './service-log-search.component.html',
  styleUrls: ['./service-log-search.component.scss'],
  providers: [DatePipe]
})
export class ServiceLogSearchComponent implements OnInit {
  serviceLogs: ServiceLog[];
  form: FormGroup;
  submitted = false;


  constructor(
    private serviceLogService: ServiceLogService,
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private messageService: MessageService
  ) {

    this.form = this.formBuilder.group({
      sourceIp: ['', Validators.required],
      destIp: ['', Validators.required],
      path: ['', Validators.required],
      protocol: ['', Validators.required],
      status: ['', Validators.required],
      country: ['', Validators.required],
      time: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.search();
  }

  get f(): any { return this.form.controls; }

  removeFormInputs(): void {
    this.form.reset();
    this.f.sourceIp.value = '';
    this.f.destIp.value = '';
    this.f.path.value = '';
    this.f.protocol.value = '';
    this.f.status.value = '';
    this.f.time.value = '';
  }

  search(): void {

    const params = {
      sourceIp: this.f.sourceIp.value,
      destIp: this.f.destIp.value,
      path: this.f.path.value,
      protocol: this.f.protocol.value,
      status: this.f.status.value,
      time: this.f.time.value? this.datePipe.transform(this.f.time.value, "yyyy-MM-ddThh:mm:ssZZZZZ"): this.f.time.value
    };
    this.serviceLogService.search(params).subscribe((serviceLogs) =>
    {
      this.serviceLogs = serviceLogs;
      this.submitted = true;
      this.removeFormInputs()
    });
  }

  isStatusOutOfBound(): boolean {
    if (this.f.status.value >= 100 && this.f.status.value < 600) {
      return true;
    }
    return false;
  }

  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }

  isStatusValid(): boolean {
    if (!this.f.status.value || this.f.status.value < 100 || this.f.status.value > 599){
      return false;
    }
    return true;
  }

}
