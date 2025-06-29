import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private baseUrl = '/api';

  constructor(private http: HttpClient) { }

  sendRequest(requestPayload: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/requests/send`, requestPayload);
  }

  getEnvironments(): Observable<any> {
    return this.http.get(`${this.baseUrl}/environments`);
  }

  getCollections(): Observable<any> {
    return this.http.get(`${this.baseUrl}/collections`);
  }

  // Add other API methods as needed
}
