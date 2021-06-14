import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'projects/hospital-ui/src/environments/environment';
import { Observable } from 'rxjs';
import { ServiceLog } from './ServiceLog';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class ServiceLogService {
  url = `${environment.apiUrl}/api`;

  constructor(private http: HttpClient) { }

  search(params): Observable<ServiceLog[]> {
    return this.http.get<ServiceLog[]>(`${this.url}/service-log/search`, 
    {
      params: {
      'sourceIp': params.sourceIp,
      'destIp': params.destIp,
      'path': params.path,
      'protocol': params.protocol,
      'status': params.status,
      'time': params.time
      }
    });
  }

}