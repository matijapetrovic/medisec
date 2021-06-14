import { HttpClient, HttpHeaders, HttpParams  } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'projects/hospital-ui/src/environments/environment';
import { Observable } from 'rxjs';
import { Rule } from './Rule';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  })
};

@Injectable({
  providedIn: 'root'
})
export class RuleService {
  url = `${environment.apiUrl}/api/rule`;

  constructor(private http: HttpClient) { }

  getTemplate(): Observable<string> {
    return this.http.get(`${this.url}/template`, {responseType: 'text'});
  }

  createRule(rule: Rule): Observable<void>{
    const url = `${this.url}`
    return this.http.post<void>(url, rule);
  }

}
