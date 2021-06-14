import { HttpClient, HttpHeaders, HttpParams  } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'projects/hospital-ui/src/environments/environment';
import { Observable } from 'rxjs';
import { Certificate } from './certificate'

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class CertificateService {
  url = `${environment.apiUrl}/api/certificates`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<Certificate[]> {
    return this.http.get<Certificate[]>(this.url);
  }

  revokeCertificate(sn: string, alias: string) {
    return this.http.post<void>(`${this.url}/${sn}/revoke`, {reason: 1, alias});
  }

}
