import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpRequest,
  HttpHeaders,
  HttpEvent,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Fichier } from 'src/app/models/Fichier';
@Injectable({
  providedIn: 'root',
})
export class UploadDocumentService {
  private baseUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) {}

  getdocuments(): Observable<Fichier[]> {
    return this.http.get<Fichier[]>(this.baseUrl + 'documents');
  }

  addDocument(
    name: string,
    docUrl: string,
    nomservice: string
  ): Observable<any> {
    const body = {
      name: name,
      docUrl: docUrl,
      nomservice: nomservice,
    };
    return this.http.post<any>(this.baseUrl + 'adddocument', body);
  }

  getServices(): Observable<string[]> {
    return this.http.get<string[]>(this.baseUrl + 'services');
  }

  getDocumentsByService(serviceName: string): Observable<Fichier[]> {
    return this.http.get<Fichier[]>(this.baseUrl + 'documents/' + serviceName);
  }

  deleteDocument(id: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}documents/${id}`);
  }
}
