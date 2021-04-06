import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CertificateRequest } from './certificate-request';
import { Certificate } from './pages/certificates/certificate';


@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private http: HttpClient) { }

  getCertificateRequests(): Observable<CertificateRequest[]> {
    return this.http.get<CertificateRequest[]>(` ${environment.apiUrl}/api/crs`);
  }

  getCsr(id: number) {
    return this.http.get<CertificateRequest>(`${environment.apiUrl}/api/csr/${id}`);
  }

  getCertificates(){
    return this.http.get<Certificate[]>(`${environment.apiUrl}/api/certificates`)
  }
}
