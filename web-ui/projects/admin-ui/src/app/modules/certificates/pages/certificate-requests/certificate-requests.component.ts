import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CertificateRequest } from '../../certificate-request';
import { CertificateService } from '../../certificate.service';

@Component({
  selector: 'app-certificate-requests',
  templateUrl: './certificate-requests.component.html',
  styleUrls: ['./certificate-requests.component.scss']
})
export class CertificateRequestsComponent implements OnInit {

  certificateRequests: CertificateRequest[];

  constructor(
    private certificateService: CertificateService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.certificateService.getCertificateRequests().subscribe((certificateRequests) => this.certificateRequests = certificateRequests);
  }

  issueCertificate(csrId: number): void {
    this.router.navigate(['certificates', 'requests', csrId, 'issue']);
  }

}
