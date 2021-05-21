import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, of, scheduled } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { CSR } from './csr';
import { environment } from 'projects/hospital-ui/src/environments/environment';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class CsrService {
  csrUrl = `${environment.apiUrl}/api`;


  constructor(private http: HttpClient) {
  }

  sendCSR(csr: CSR): Observable<void> {
    const url = `${this.csrUrl}/csr`;
    console.log(url)
    return this.http.post<void>(url, csr);
  }
}
