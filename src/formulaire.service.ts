import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Formulaire } from '../models/formulaire.model';

@Injectable({
  providedIn: 'root',
})
export class FormulaireService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getAllFormulaires(): Observable<Formulaire[]> {
    return this.http.get<Formulaire[]>(this.baseUrl + '/formulaires');
  }

  addFormulaire(formulaire: Formulaire): Observable<Formulaire> {
    return this.http.post<Formulaire>(
      this.baseUrl + '/addformulaire',
      formulaire
    );
  }

  deleteFormulaire(id: number): Observable<void> {
    const url = `${this.baseUrl}/formulaire/${id}`;
    return this.http.delete<void>(url);
  }
}
