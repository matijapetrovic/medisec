import { HttpClient, HttpHeaders, HttpParams  } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'projects/hospital-ui/src/environments/environment';
import { Observable } from 'rxjs';
import { Rule } from '../rules/Rule';
import { MedicalRecordLog } from './MedicalRecordLog';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class MedicalRecordLogsService {
  url = `${environment.apiUrl}/api`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<MedicalRecordLog[]> {
    return this.http.get<MedicalRecordLog[]>(`${this.url}/medical-record-log`);
  }

}
