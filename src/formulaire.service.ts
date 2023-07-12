import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FormulaireModel } from './app/models/FormulaireModel';

@Injectable({
  providedIn: 'root',
})
export class FormulaireService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getAllFormulaires(): Observable<FormulaireModel[]> {
    return this.http.get<FormulaireModel[]>(this.baseUrl + '/formulaires');
  }

  addFormulaire(formulaire: FormulaireModel): Observable<FormulaireModel> {
    return this.http.post<FormulaireModel>(
      this.baseUrl + '/addformulaire',
      formulaire
    );
  }

  deleteFormulaire(id: number): Observable<void> {
    const url = `${this.baseUrl}/formulaire/${id}`;
    return this.http.delete<void>(url);
  }
}
