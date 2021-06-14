import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'projects/hospital-ui/src/environments/environment';
import { Observable } from 'rxjs';
import { LogSource } from './log-source';

@Injectable({
  providedIn: 'root'
})
export class LogsService {
  private apiUrl = `${environment.apiUrl}/api/log-sources`;
  constructor(private http: HttpClient) { }

  getLogSources(): Observable<LogSource[]> {
    return this.http.get<LogSource[]>(this.apiUrl);
  }

  updateLogSources(logSources: LogSource[]): Observable<void> {
    return this.http.post<void>(this.apiUrl, logSources);
  }
}
