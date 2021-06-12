import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { CertificateService } from '../../certificate.service';
import { BasicConstraints, IssueCertificateData, KeyUsage } from '../../pages/certificates/certificate';

@Component({
  selector: 'app-issue-certificate-form',
  templateUrl: './issue-certificate-form.component.html',
  styleUrls: ['./issue-certificate-form.component.scss']
})
export class IssueCertificateFormComponent implements OnInit {

  @Input()
  private csrId: number;

  form: FormGroup;

  minStartDate: Date;
  minEndDate: Date;

  extensions: boolean;

  issuerAliases: String[];

  constructor(private certificateService: CertificateService,
    private router: Router) {
    this.form = new FormGroup({
      subjectData: new FormGroup({
        subjectId: new FormControl(''),
        commonName: new FormControl(''),
        surname: new FormControl(''),
        givenName: new FormControl(''),
        organization: new FormControl(''),
        organizationUnit: new FormControl(''),
        countryCode: new FormControl(''),
        email: new FormControl('')
      }),
      issuerAlias: new FormControl(''),
      startDate: new FormControl(null),
      endDate: new FormControl(null),
      basicConstraintsEnabled: new FormControl(false),
      basicConstraints: new FormGroup({
        isCa: new FormControl(false),
        pathLen: new FormControl({ value: null, disabled: false}),
        isCritical: new FormControl(false)
      }),
      keyUsageEnabled: new FormControl(false),
      keyUsage: new FormGroup({
        crlSign: new FormControl(false),
        dataEncipherment: new FormControl(false),
        decipherOnly: new FormControl(false),
        keyAgreement: new FormControl(false),
        keyCertSign: new FormControl(false),
        keyEncipherment: new FormControl(false),
        nonRepudiation: new FormControl(false),
        digitalSignature: new FormControl(false),
        encipherOnly: new FormControl(false),
        isCritical: new FormControl(false),
      }),
      subjectKeyId: new FormControl(false)
    });
  }

  ngOnInit(): void {
    this.certificateService.getIssuerAliases().subscribe((issuerAliases) => this.issuerAliases = issuerAliases);
    if (this.csrId) {
      this.certificateService.getCsr(this.csrId).subscribe((csr) => {
        this.form.patchValue({
          subjectData: {
            commonName: csr.commonName,
            surname: csr.surname,
            givenName: csr.givenName,
            organization: csr.organization,
            organizationUnit: csr.organizationUnit,
            countryCode: csr.countryCode,
            email: csr.email,
          }
        });
      });
    }
  }

  get f(): any { return this.form.value; }

  onSubmit(): void  {
    console.log(this.f);
    let basicConstraints: BasicConstraints = null;
    if (this.f.basicConstraintsEnabled) {
      basicConstraints = {
        ca: this.f.basicConstraints.isCa,
        pathLen: this.f.basicConstraints.pathLen,
        basicConstraintsIsCritical: this.f.basicConstraints.isCritical
      };
    }

    let keyUsage: KeyUsage = null;
    if (this.f.keyUsageEnabled) {
      keyUsage = {
        crlSign: this.f.keyUsage.crlSign,
        dataEncipherment: this.f.keyUsage.dataEncipherment,
        decipherOnly: this.f.keyUsage.decipherOnly,
        keyAgreement: this.f.keyUsage.keyAgreement,
        keyCertSign: this.f.keyUsage.keyCertSign,
        keyEncipherment: this.f.keyUsage.keyEncipherment,
        nonRepudiation: this.f.keyUsage.nonRepudiation,
        digitalSignature: this.f.keyUsage.digitalSignature,
        encipherOnly: this.f.keyUsage.encipherOnly,
        keyUsageIsCritical: this.f.keyUsage.isCritical
      }
    }

    const certificateData: IssueCertificateData = {
      csrId: this.csrId,
      issuerAlias: "bongoissuer",
      subjectData: {
        subjectId: this.f.subjectData.subjectId,
        commonName: this.f.subjectData.commonName,
        surname: this.f.subjectData.surname,
        givenName: this.f.subjectData.givenName,
        organization: this.f.subjectData.organization,
        organizationUnitName: this.f.subjectData.organizationUnit,
        countryCode: this.f.subjectData.countryCode,
        email: this.f.subjectData.email,
      },
      startDate: this.f.startDate,
      endDate: this.f.endDate,
      extensions: {
        keyUsage,
        basicConstraints,
        subjectKeyId: this.f.subjectKeyId
      }
    }
    this.certificateService.addSertificate(certificateData).subscribe(() => {
      this.router.navigate(['certificates']);
    });
  }

}
