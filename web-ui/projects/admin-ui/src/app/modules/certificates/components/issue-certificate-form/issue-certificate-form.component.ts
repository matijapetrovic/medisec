import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { CertificateService } from '../../certificate.service';
import { BasicConstraints, Extensions, IssueCertificateData, KeyUsage, Template } from '../../pages/certificates/certificate';

@Component({
  selector: 'app-issue-certificate-form',
  templateUrl: './issue-certificate-form.component.html',
  styleUrls: ['./issue-certificate-form.component.scss']
})
export class IssueCertificateFormComponent implements OnInit {

  @Input()
  private csrId: string;

  form: FormGroup;

  minStartDate: Date;
  minEndDate: Date;

  extensions: boolean;

  issuerAliases: any[];
  templates: Template[];

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
      templateName: new FormControl(''),
      template: new FormControl(null),
      issuerAlias: new FormControl(''),
      startDate: new FormControl(null),
      endDate: new FormControl(null),
      basicConstraintsEnabled: new FormControl(false),
      basicConstraints: new FormGroup({
        ca: new FormControl(false),
        pathLen: new FormControl({ value: null, disabled: false}),
        basicConstraintsIsCritical: new FormControl(false)
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
        keyUsageIsCritical: new FormControl(false),
      }),
      subjectKeyId: new FormControl(false),
      authorityKeyId: new FormControl(false),
    });
  }

  ngOnInit(): void {
    this.getTemplates();
    this.certificateService.getIssuerAliases().subscribe((issuerAliases) => {
      this.issuerAliases = issuerAliases.map((alias) => { return {label: alias, value: alias}});
    });
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
        ca: this.f.basicConstraints.ca,
        pathLen: this.f.basicConstraints.pathLen,
        basicConstraintsIsCritical: this.f.basicConstraints.basicConstraintsIsCritical
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
        keyUsageIsCritical: this.f.keyUsage.keyUsageIsCritical
      }
    }

    const certificateData: IssueCertificateData = {
      csrId: this.csrId,
      issuerAlias: this.f.issuerAlias,
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
        subjectKeyId: this.f.subjectKeyId,
        authorityKeyId: this.f.authorityKeyId
      }
    }
    this.certificateService.addSertificate(certificateData).subscribe(() => {
      this.router.navigate(['certificates']);
    });
  }

  updateExtensions() {
    const extensions: Extensions = JSON.parse(JSON.stringify(this.f.template.extensions));
    if (!extensions) {
      this.form.controls['keyUsage'].reset();
      this.form.controls['basicConstraints'].reset();
      this.form.controls['subjectKeyId'].reset();
      this.form.controls['authorityKeyId'].reset();
      return;
    }
    let basicConstraintsEnabled = true;
    let keyUsageEnabled = true;
    if (!extensions.basicConstraints) {
      basicConstraintsEnabled = false;
      extensions.basicConstraints = {
        ca: false,
        pathLen: null,
        basicConstraintsIsCritical: false
      }
    }
    if (!extensions.keyUsage) {
      keyUsageEnabled = false;
      extensions.keyUsage = {
        crlSign: false,
        dataEncipherment: false,
        decipherOnly: false,
        keyAgreement: false,
        keyCertSign: false,
        keyEncipherment: false,
        nonRepudiation: false,
        digitalSignature: false,
        encipherOnly: false,
        keyUsageIsCritical: false
      }
    }
    this.form.patchValue({
      basicConstraintsEnabled: basicConstraintsEnabled,
      keyUsageEnabled: keyUsageEnabled,
      keyUsage: extensions.keyUsage,
      basicConstraints: extensions.basicConstraints,
      subjectKeyId: extensions.subjectKeyId,
      authorityKeyId: extensions.authorityKeyId
    });
  }

  getTemplates(): void {
    this.certificateService.getTemplates().subscribe((templates) => this.templates = templates);
  }

  saveTemplate(): void {
    const templateName = this.f.templateName;
    let basicConstraints: BasicConstraints = null;
    if (this.f.basicConstraintsEnabled) {
      basicConstraints = {
        ca: this.f.basicConstraints.ca,
        pathLen: this.f.basicConstraints.pathLen,
        basicConstraintsIsCritical: this.f.basicConstraints.basicConstraintsIsCritical
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
        keyUsageIsCritical: this.f.keyUsage.keyUsageIsCritical
      }
    }
    const extensions = {
      keyUsage,
      basicConstraints,
      subjectKeyId: this.f.subjectKeyId,
      authorityKeyId: this.f.authorityKeyId
    };
    this.certificateService.saveTemplate(templateName, extensions).subscribe(() => {
      this.form.controls['templateName'].reset();
      this.getTemplates();
    });
  }

}
