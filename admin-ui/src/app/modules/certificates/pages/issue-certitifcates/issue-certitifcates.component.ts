import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CertificateRequest } from '../../certificate-request';
import { CertificateService } from '../../certificate.service';

@Component({
  selector: 'app-issue-certitifcates',
  templateUrl: './issue-certitifcates.component.html',
  styleUrls: ['./issue-certitifcates.component.scss']
})
export class IssueCertitifcatesComponent implements OnInit {

  private csrId: number;
  form: FormGroup;

  constructor(private route: ActivatedRoute, private certificateService: CertificateService, formBuilder: FormBuilder) {
    this.form = formBuilder.group({
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
    this.csrId = +this.route.snapshot.params.csrId;
    this.certificateService.getCsr(this.csrId).subscribe((csr) => {
      this.form['commonName'] = csr.commonName;
      this.form['surname'] = csr.surname;
      this.form['givenName'] = csr.givenName;
      this.form['organization'] = csr.organization;
      this.form['organizationUnit'] = csr.organizationUnit;
      this.form['countryCode'] = csr.countryCode;
      this.form['email'] = csr.email;
    });
  }

}
