import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'projects/hospital-ui/src/environments/environment';
import { Observable } from 'rxjs';
import { ChartAlarmData, ServiceLogReport } from './report';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  url = `${environment.apiUrl}/api`;

  constructor(private http: HttpClient) { }

  getReport(): Observable<ServiceLogReport> {
    return this.http.get<ServiceLogReport>(`${this.url}/reports/service-log`);
  }

  getChartData(): Observable<ChartAlarmData>{
    return this.http.get<ChartAlarmData>(`${this.url}/reports/service-log-alarm`);
  }

}
