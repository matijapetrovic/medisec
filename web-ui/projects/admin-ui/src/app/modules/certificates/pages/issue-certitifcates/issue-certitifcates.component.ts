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

  csrId: number = null;

  constructor(
    private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    if (!this.route.snapshot.params.csrId)
      return;
    this.csrId = +this.route.snapshot.params.csrId;
  }

}
