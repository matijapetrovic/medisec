import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'projects/admin-ui/src/environments/environment';
import { Observable } from 'rxjs';
import { CertificateRequest } from './certificate-request';
import { RevocationReason } from '../certificates/revocation-reason';
import { Certificate, IssueCertificateData } from './pages/certificates/certificate';


@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private http: HttpClient) { }

  getCertificateRequests(): Observable<CertificateRequest[]> {
    return this.http.get<CertificateRequest[]>(` ${environment.apiUrl}/api/certificate-requests`);
  }

  getCsr(id: number) {
    return this.http.get<CertificateRequest>(`${environment.apiUrl}/api/certificate-requests/${id}`);
  }

  getCertificates(){
    return this.http.get<Certificate[]>(`${environment.apiUrl}/api/certificates`)
  }

  getRevocationReasons(){
    return this.http.get<RevocationReason[]>(`${environment.apiUrl}/api/certificates/revocation-reasons`)
  }

  addSertificate(certificate: IssueCertificateData) {
    const url = `${environment.apiUrl}/api/certificates`;
    console.log(url)
    return this.http.post<void>(url, certificate)
      .pipe();
  }

  revokeCertificate(sn: string, alias: string, reason:number) {
    return this.http.post<void>(`${environment.apiUrl}/api/certificates/${sn}/revoke`, {reason: 1, alias});
  }
}
