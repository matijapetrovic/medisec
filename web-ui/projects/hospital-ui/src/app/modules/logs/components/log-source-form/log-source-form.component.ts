import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { DynamicDialogRef, DynamicDialogConfig } from 'primeng/dynamicdialog';
import { LogSource } from '../../log-source';

@Component({
  selector: 'app-log-source-form',
  templateUrl: './log-source-form.component.html',
  styleUrls: ['./log-source-form.component.scss']
})
export class LogSourceFormComponent implements OnInit {
  source: LogSource;
  form: FormGroup;

  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig) {
    this.form = new FormGroup({
      path: new FormControl(''),
      readFrequency: new FormControl(0),
      filter: new FormControl('')
    });
  }
  ngOnInit(): void {
    this.source = this.config.data['source'];
    this.form.patchValue({
      path: this.source.path,
      readFrequency: this.source.readFrequency,
      filter: this.source.filter
    });
  }

  submitForm() {
    this.source.path = this.form.controls['path'].value;
    this.source.readFrequency = this.form.controls['readFrequency'].value;
    this.source.filter = this.form.controls['filter'].value;
    this.ref.close();
  }
}
