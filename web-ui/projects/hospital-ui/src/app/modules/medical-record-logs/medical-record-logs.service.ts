import { HttpClient, HttpHeaders, HttpParams  } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'projects/admin-ui/src/environments/environment';
import { Observable } from 'rxjs';
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
  //url = `${environment.apiUrl}/api`;
  url = 'https://localhost:8481/api/medical-record/getAll';

  constructor(private http: HttpClient) { }

  getAll(): Observable<MedicalRecordLog[]> {
    return this.http.get<MedicalRecordLog[]>(this.url);
  }
  
}
