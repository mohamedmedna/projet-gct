import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormulaireService } from 'src/formulaire.service';
import { FormulaireModel } from '../models/FormulaireModel';

@Component({
  selector: 'app-formulaire-list',
  templateUrl: './formulaire-list.component.html',
  styleUrls: ['./formulaire-list.component.css'],
})
export class FormulaireListComponent implements OnInit {
  formulaires: FormulaireModel[] = [];
  constructor(
    private formulaireService: FormulaireService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getFormulaires();
  }

  getFormulaires(): void {
    this.formulaireService
      .getAllFormulaires()
      .subscribe((formulaires: FormulaireModel[]) => {
        this.formulaires = formulaires;
      });
  }

  toggleModifier(formulaire: FormulaireModel): void {
    formulaire.modifier = !formulaire.modifier;
    this.router.navigate(['/modifiers-champs', formulaire.id]);
  }

  /*deleteFormulaire(formulaire: FormulaireModel): void {
    this.formulaireService.deleteFormulaire(formulaire.id).subscribe(() => {
      this.formulaires = this.formulaires.filter((f) => f.id !== formulaire.id);
    });
  }*/
  confirmDelete(formulaire: FormulaireModel): void {
    const confirmation = confirm(
      'Êtes-vous sûr de vouloir supprimer ce formulaire ?'
    );

    if (confirmation) {
      this.deleteFormulaire(formulaire);
    }
  }

  deleteFormulaire(formulaire: FormulaireModel): void {
    this.formulaireService.deleteFormulaire(formulaire.id).subscribe(() => {
      this.formulaires = this.formulaires.filter((f) => f.id !== formulaire.id);
    });
  }
}
