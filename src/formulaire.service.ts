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
    return this.http.get<FormulaireModel[]>(`${this.baseUrl}/formulaires`);
  }

  addFormulaire(formulaire: FormulaireModel): Observable<FormulaireModel> {
    return this.http.post<FormulaireModel>(
      `${this.baseUrl}/addformulaire`,
      formulaire
    );
  }

  updateFormulaire(
    id: number,
    formulaire: FormulaireModel
  ): Observable<FormulaireModel> {
    const url = `${this.baseUrl}/formulaire/${id}`;
    return this.http.put<FormulaireModel>(url, formulaire);
  }

  getFormulaireById(id: number): Observable<FormulaireModel> {
    const url = `${this.baseUrl}/formulaire/${id}`;
    return this.http.get<FormulaireModel>(url);
  }

  updateChampVisibility(
    id: number,
    champ: string,
    visible: boolean
  ): Observable<FormulaireModel> {
    const url = `${this.baseUrl}/${id}/update-champ-visibility`;
    const params = {
      champ: champ,
      visible: visible.toString(),
    };
    return this.http.put<FormulaireModel>(url, null, { params: params });
  }

  deleteFormulaire(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/formulaire/${id}`);
  }
}
