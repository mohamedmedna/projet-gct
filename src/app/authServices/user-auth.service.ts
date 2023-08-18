import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserAuthService {
  constructor() {}

  setRoles(roles: []) {
    localStorage.setItem('roles', JSON.stringify(roles));
  }

  getRoles(): string {
    return JSON.parse(localStorage.getItem('roles') || 'null' || '{}');
  }

  setToken(jwtToken: string) {
    localStorage.setItem('jwtToken', jwtToken);
  }

  getToken(): any {
    return localStorage.getItem('jwtToken');
  }
  clear() {
    localStorage.clear();
  }

  isLoggedIn() {
    return this.getRoles() && this.getToken();
  }
}
