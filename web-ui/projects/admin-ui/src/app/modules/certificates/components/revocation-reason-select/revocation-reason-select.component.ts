import { Component, OnInit } from '@angular/core';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { CertificateService } from '../../certificate.service';
import { RevocationReason } from '../../revocation-reason';

@Component({
  selector: 'app-revocation-reason-select',
  templateUrl: './revocation-reason-select.component.html',
  styleUrls: ['./revocation-reason-select.component.scss']
})
export class RevocationReasonSelectComponent implements OnInit {

  reasons: RevocationReason[];
  selectedReason: RevocationReason;

  constructor(public ref: DynamicDialogRef, private service: CertificateService) {}

  ngOnInit(): void {
    this.service.getRevocationReasons().subscribe((data) => this.reasons = data);
  }

  revoke(): void{
    if(this.selectedReason == undefined)
      this.selectedReason = this.reasons[0];
    this.ref.close(this.selectedReason);
  }
  cancel():void{
    this.ref.close();
  }
}
