import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpRequest,
  HttpHeaders,
  HttpEvent,
  HttpResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Fichier } from 'src/app/models/Fichier';
import { FormulaireModel } from './models/FormulaireModel';

@Injectable({
  providedIn: 'root',
})
export class UploadDocumentService {
  private baseUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) {}

  getdocuments(): Observable<Fichier[]> {
    return this.http.get<Fichier[]>(this.baseUrl + 'documents');
  }

  getGeneratedDocuments(): Observable<Fichier[]> {
    return this.http.get<Fichier[]>(this.baseUrl + 'generateddocuments');
  }

  public addDocument(
    name: string,
    nomservice: string,
    file: File,
    formulairenom: string
  ): Observable<any> {
    const formData = new FormData();
    formData.append('name', name);
    formData.append('nomservice', nomservice);
    formData.append('file', file);
    formData.append('formulairenom', formulairenom);

    return this.http.post(this.baseUrl + 'adddocument', formData, {
      responseType: 'text',
    });
  }

  getServices(): Observable<string[]> {
    return this.http.get<string[]>(this.baseUrl + 'servicess');
  }

  getDocumentsByService(serviceName: string): Observable<Fichier[]> {
    return this.http.get<Fichier[]>(this.baseUrl + 'documents/' + serviceName);
  }
  getDocumentsGeneratedByService(serviceName: string): Observable<Fichier[]> {
    return this.http.get<Fichier[]>(
      this.baseUrl + 'generateddocuments/' + serviceName
    );
  }

  /* getDownloadUrl(iddoc: number): string {
    return `${this.baseUrl}download/${iddoc}`;
  }*/

  downloadDocument(iddoc: number): Observable<any> {
    const url = `${this.baseUrl}download/${iddoc}`;

    return this.http.get(url, { responseType: 'blob' });
  }

  downloadGeneratedDocument(iddoc: number): Observable<any> {
    const url = `${this.baseUrl}generatedDownload/${iddoc}`;
    return this.http.get(url, { responseType: 'blob' });
  }

  deleteDocument(id: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}documents/${id}`);
  }

  deleteGeneratedDocument(id: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}generateddocuments/${id}`);
  }

  generateUpdatedDocx(documentId: number, formulaire: any): Observable<Blob> {
    const url = `${this.baseUrl}${documentId}/generate-updated-docx`;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      responseType: 'blob' as 'json',
    };

    return this.http.post<Blob>(url, formulaire, httpOptions);
  }
}
