import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Role } from './models/Role';

@Injectable({
  providedIn: 'root'
})
export class RoleServiceService {
  constructor(private http: HttpClient) { }
  private baseUrl = 'http://localhost:8080/';

  addrole(data: any): Observable<any> {
    return this.http.post(this.baseUrl + 'addrole', data);
  }
  updateRole(id: any, data: any): Observable<any> {
    return this.http.put(this.baseUrl + 'role/' + id, data);
  }

  getRoless(): Observable<Role[]> {
    return this.http.get<Role[]>(this.baseUrl + 'roles');
  }

  deleteRole(id: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}role/${id}`);
  }
}
