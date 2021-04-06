import { Component, OnInit } from '@angular/core';
import { CertificateService } from '../../certificate.service';
import { Certificate } from './certificate';

@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrls: ['./certificates.component.scss']
})
export class CertificatesComponent implements OnInit {

  certificates: Certificate[];

  constructor(private service: CertificateService) { }

  ngOnInit(): void {
    this.getCertificates();
  }

  getCertificates() {
    this.service.getCertificates().subscribe(certificates => this.certificates = certificates);
  }

  revokeCertificate(alias: string) {
    this.service.revokeCertificate("1", alias).subscribe(() => this.getCertificates());
  }

}
