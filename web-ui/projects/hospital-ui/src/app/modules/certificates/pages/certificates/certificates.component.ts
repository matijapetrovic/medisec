import { Component, OnInit } from '@angular/core';
import { Certificate } from '../../certificate';
import { CertificateService } from '../../certificate.service';

@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrls: ['./certificates.component.scss']
})
export class CertificatesComponent implements OnInit {

  certificates: Certificate[];

  constructor(private service: CertificateService) { }

  ngOnInit(): void {
    this.service.getAll().subscribe(data => this.certificates = data);
  }

  revokeCertificate(serialNumber: any){
    this.service.revokeCertificate(serialNumber, "bongcloud").subscribe(data => {});
  }
}
