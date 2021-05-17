import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-issue-certificate-form',
  templateUrl: './issue-certificate-form.component.html',
  styleUrls: ['./issue-certificate-form.component.scss']
})
export class IssueCertificateFormComponent implements OnInit {

  form: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.form = this.formBuilder.group({
      commonName: '',
      surname: '',
      givenName: '',
      organization: '',
      organizationUnit: '',
      countryCode: '',
      email: ''
    });
  }

  ngOnInit(): void {
  }

}
