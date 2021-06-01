import { Component, OnInit } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { CertificateService } from '../../certificate.service';
import { RevocationReasonSelectComponent } from '../../components/revocation-reason-select/revocation-reason-select.component';
import { RevocationReason } from '../../revocation-reason';
import { Certificate } from './certificate';

@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrls: ['./certificates.component.scss'],
  providers: [DialogService]
})
export class CertificatesComponent implements OnInit {

  certificates: Certificate[];
  reasons: RevocationReason[];
  ref: DynamicDialogRef;

  constructor(private service: CertificateService,
              private dialogService: DialogService) {
               }

  ngOnInit(): void {
    this.getCertificates();
  }

  getCertificates() {
    this.service.getCertificates().subscribe(certificates => this.certificates = certificates);
  }

  revokeCertificate(serialNumber: string) {
    this.ref = this.dialogService.open(RevocationReasonSelectComponent, {
      header: 'Choose a revocation reason',
      width: '30%',
      contentStyle: {"height": "300px", "overflow": "auto"},
      baseZIndex: 10000,
  });
    this.ref.onClose.subscribe((reason: RevocationReason) => {
      if(reason){
        console.log(reason.key);
        this.service.revokeCertificate(serialNumber, "bongcloud", reason.key).subscribe(() => this.getCertificates());
      }
    })
  }

}
