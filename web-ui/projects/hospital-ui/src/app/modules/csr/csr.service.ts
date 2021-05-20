import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HandleError, HttpErrorHandler } from '../../core/services/http-error-handler.service';

import { Observable, of, scheduled } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { CSR } from './csr';
import { MessageService } from 'primeng/api';
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
  csrUrl = `${environment.apiUrl}/api/hospital-service`;
  private handleError: HandleError;


  constructor(private http: HttpClient, private messageService: MessageService, httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('CsrService');
  }

  sendCSR(csr: CSR): Observable<void> {
    const url = `${this.csrUrl}/csr`;
    console.log(url)
    return this.http.post<void>(url, csr)
      .pipe(
        catchError(this.handleError<void>('sendCsr'))
      );
  }
}
