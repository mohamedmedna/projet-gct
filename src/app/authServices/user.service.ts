import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserAuthService } from './user-auth.service';
import { Observable } from 'rxjs';
import { User } from '../models/User';

const baseUrl = 'http://localhost:8080';
@Injectable({
  providedIn: 'root',
})
export class UserService {
  requestHeader = new HttpHeaders({ 'No-Auth': 'True' });

  constructor(
    private httpclient: HttpClient,
    private userAuth: UserAuthService
  ) {}

  getUsers(): Observable<User[]> {
    return this.httpclient.get<User[]>(baseUrl + '/users');
  }

  public addUser(
    userName: string,
    nomPrenom: string,
    userPassword: string,
    nomService: string,
    roleNames: string[]
  ): Observable<any> {
    const formData = new FormData();
    formData.append('userName', userName);
    formData.append('nomPrenom', nomPrenom);
    formData.append('userPassword', userPassword);
    for (const roleName of roleNames) {
      formData.append('roleNames', roleName);
    }
    formData.append('nomservice', nomService);

    return this.httpclient.post(baseUrl + '/addUser', formData, {
      responseType: 'text',
    });
  }

  update(id: any, data: any): Observable<any> {
    return this.httpclient.put(baseUrl + '/user' + '/' + id, data);
  }

  getUserById(id: any): Observable<User> {
    return this.httpclient.get(baseUrl + '/user' + id);
  }

  deleteUser(id: number): Observable<any> {
    return this.httpclient.delete<any>(`${baseUrl}/users/${id}`);
  }

  getRoles(): Observable<string[]> {
    return this.httpclient.get<string[]>(baseUrl + '/roless');
  }

  login(loginData: any) {
    return this.httpclient.post(baseUrl + '/authenticate', loginData, {
      headers: this.requestHeader,
    });
  }

  roleMatch(allowedRoles: any): any {
    let isMatch = false;
    const userRoles: any = this.userAuth.getRoles();

    if (userRoles != null && userRoles) {
      for (let i = 0; i < userRoles.length; i++) {
        for (let j = 0; j < allowedRoles.length; j++) {
          if (userRoles[i].roleName === allowedRoles[j]) {
            isMatch = true;
            return isMatch;
          } else {
            return isMatch;
          }
        }
      }
    }
  }
}
