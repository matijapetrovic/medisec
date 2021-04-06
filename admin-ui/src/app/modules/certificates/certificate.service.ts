import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CertificateRequest } from './certificate-request';
import { Certificate, IssueCertificateData } from './pages/certificates/certificate';


@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private http: HttpClient) { }

  getCertificateRequests(): Observable<CertificateRequest[]> {
    return this.http.get<CertificateRequest[]>(` ${environment.apiUrl}/api/csr`);
  }

  getCsr(id: number) {
    return this.http.get<CertificateRequest>(`${environment.apiUrl}/api/csr/${id}`);
  }

  getCertificates(){
    return this.http.get<Certificate[]>(`${environment.apiUrl}/api/certificates`)
  }

  addSertificate(certificate: IssueCertificateData) {
    const url = `${environment.apiUrl}/api/certificates`;
    console.log(url)
    return this.http.post<void>(url, certificate)
      .pipe();
  }
}
