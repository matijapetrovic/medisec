import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CertificateService } from '../../certificate.service';
import { IssueCertificateData } from '../certificates/certificate';

@Component({
  selector: 'app-issue-certitifcates',
  templateUrl: './issue-certitifcates.component.html',
  styleUrls: ['./issue-certitifcates.component.scss']
})
export class IssueCertitifcatesComponent implements OnInit {

  private csrId: number;
  form: FormGroup;

  constructor(
    private route: ActivatedRoute, 
    private certificateService: CertificateService, 
    private router: Router,
    formBuilder: FormBuilder) {
    this.form = formBuilder.group({
      commonName: '',
      surname: '',
      givenName: '',
      organization: '',
      organizationUnit: '',
      countryCode: '',
      email: '',
      startDate: '',
      endDate: ''
    });
  }

  ngOnInit(): void {
    this.csrId = +this.route.snapshot.params.csrId;
    this.certificateService.getCsr(this.csrId).subscribe((csr) => {
      this.form.patchValue({
        commonName: csr.commonName,
        surname: csr.surname,
        givenName: csr.givenName,
        organization: csr.organization,
        organizationUnit: csr.organizationUnit,
        countryCode: csr.countryCode,
        email: csr.email,
      });
    });
  }

  get f(): any { return this.form.controls; }

  onSubmit(): void {
    const certificateData: IssueCertificateData = {
      csrId: this.csrId,
      commonName: this.f.commonName.value,
      surname: this.f.surname.value,
      givenName: this.f.givenName.value,
      organization: this.f.organization.value,
      organizationUnitName: this.f.organizationUnit.value,
      countryCode: this.f.countryCode.value,
      email: this.f.email.value,
      startDate: this.f.startDate.value,
      endDate: this.f.endDate.value,
    }
    this.certificateService.addSertificate(certificateData).subscribe(() => {
      this.router.navigate(['certificates']);
    });
  }

}
