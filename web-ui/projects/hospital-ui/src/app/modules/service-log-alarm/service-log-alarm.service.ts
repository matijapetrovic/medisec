import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'projects/hospital-ui/src/environments/environment';
import { Observable } from 'rxjs';
import { ServiceLogAlarm } from './ServiceLogAlarm';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class ServiceLogAlarmService {
  url = `${environment.apiUrl}/api`;

  constructor(private http: HttpClient) { }

  findAll(): Observable<ServiceLogAlarm[]> {
    return this.http.get<ServiceLogAlarm[]>(`${this.url}/service-log-alarm`);
  }

}