import { HttpClient, HttpHeaders, HttpParams  } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'projects/hospital-ui/src/environments/environment';
import { Observable } from 'rxjs';
import { MedicalRecordAlarm } from './MedicalRecordAlarm';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class MedicalRecordAlarmService {
  url = `${environment.apiUrl}/api`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<MedicalRecordAlarm[]> {
    return this.http.get<MedicalRecordAlarm[]>(`${this.url}/medical-record-alarm`);
  }

}
