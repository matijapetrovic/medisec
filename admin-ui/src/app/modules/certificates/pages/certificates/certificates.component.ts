import { Component, OnInit } from '@angular/core';
import { Certificate } from './certificate';

@Component({
  selector: 'app-certificates',
  templateUrl: './certificates.component.html',
  styleUrls: ['./certificates.component.scss']
})
export class CertificatesComponent implements OnInit {

  certificates: Certificate[];

  constructor() { }

  ngOnInit(): void {
  }

}
