import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Service } from './models/Service';

@Injectable({
  providedIn: 'root',
})
export class AddServiceService {
  constructor(private http: HttpClient) {}
  private baseUrl = 'http://localhost:8080/';

  addservice(data: any): Observable<any> {
    return this.http.post(this.baseUrl + 'addservice', data);
  }
  updateservice(id: any, data: any): Observable<any> {
    return this.http.put(this.baseUrl + 'service/' + id, data);
  }

  getServices(): Observable<Service[]> {
    return this.http.get<Service[]>(this.baseUrl + 'servics');
  }

  deleteService(id: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}service/${id}`);
  }
}
