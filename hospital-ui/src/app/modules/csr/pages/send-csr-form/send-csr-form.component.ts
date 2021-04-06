import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { CsrService } from '../../csr.service';
import { CSR } from '../../csr';

@Component({
  selector: 'app-send-csr-form',
  templateUrl: './send-csr-form.component.html',
  styleUrls: ['./send-csr-form.component.css']
})
export class SendCsrFormComponent implements OnInit {
  form: FormGroup;
  submitted = false;

  constructor(
    private csrService: CsrService,
    private formBuilder: FormBuilder,
  ) { 
    this.form = this.formBuilder.group({
      name: ['', Validators.required],
    });
  }

  ngOnInit(): void {
   
  }

  onSubmit(): void {
    this.submitted = false;
    // if (this.invalidFormInputs()) {
    //   this.removeFormInputs();
    //   return;
    // }
    this.sendCSR();
  }

  sendCSR(): void {
    console.log(this.f)
    const csr: CSR = { 
      id: NaN,
      commonName: this.f.commonName.value,
      givenName: this.f.givenName.value,
      surname: this.f.surname.value,
      organization: this.f.organization.value,
      organizationUnit: this.f.organizationUnit.value,
      country: this.f.country.value,
      email: this.f.email.value,
      uniqueIdentifier: this.f.uniqueIdentifier.value,
     };
    this.csrService.sendCSR(csr)
      .subscribe(
        () => {
          this.submitted = true;
          this.removeFormInputs();
        });
  }

  get f(): any { return this.form.controls; }

  removeFormInputs(): void {
    this.form.reset();
  }

  invalidFormInputs(): boolean {
    if (this.f.commonName.value === '' || this.f.commonName.value === null ||
      this.f.givenName.value === '' || this.f.givenName.value === null ||
      this.f.surname.value === '' || this.f.surname.value === null ||
      this.f.organization.value === '' || this.f.organization.value === null ||
      this.f.organizationUnit.value === '' || this.f.organizationUnit.value === null ||
      this.f.country.value === '' || this.f.country.value === null ||
      this.f.email.value === '' || this.f.email.value === null ||
      this.f.uniqueIdentifier.value === '' || this.f.uniqueIdentifier.value === null
      ) {
      return true;
    }
    return false;
  }

  errorMessage(): string {
    return 'Name is required!';
  }
}
