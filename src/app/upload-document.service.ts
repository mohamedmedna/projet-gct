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

  public addDocument(
    name: string,
    nomservice: string,
    file: File,
    form: string
  ): Observable<any> {
    const formData = new FormData();
    formData.append('name', name);
    formData.append('nomservice', nomservice);
    formData.append('file', file);
    formData.append('form', form);

    return this.http.post(this.baseUrl + 'adddocument', formData, {
      responseType: 'text',
    });
  }

  getServices(): Observable<string[]> {
    return this.http.get<string[]>(this.baseUrl + 'services');
  }

  getDocumentsByService(serviceName: string): Observable<Fichier[]> {
    return this.http.get<Fichier[]>(this.baseUrl + 'documents/' + serviceName);
  }

  getDownloadUrl(iddoc: number): string {
    return `${this.baseUrl}download/${iddoc}`;
  }

  deleteDocument(id: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}documents/${id}`);
  }
}
