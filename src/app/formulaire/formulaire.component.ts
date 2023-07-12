import { Component, OnInit } from '@angular/core';
import { ChampModel, FormulaireModel } from '../models/FormulaireModel';
import { FormulaireService } from '../../formulaire.service';

@Component({
  selector: 'app-formulaire',
  templateUrl: './formulaire.component.html',
  styleUrls: ['./formulaire.component.css'],
})
export class FormulaireComponent implements OnInit {
  formulaires: FormulaireModel[] = [];
  formulaire: FormulaireModel = {
    id: 0,
    nom: '',
    champs: [
      { nom: 'numConsultation', visible: true },
      { nom: 'titreConsultation', visible: true },
      { nom: 'objetConsultation', visible: true },
      { nom: 'conditionsParticipation', visible: true },
      { nom: 'delaiLivraison', visible: true },
    ],
  };

  constructor(private formulaireService: FormulaireService) {}

  ngOnInit(): void {
    this.getFormulaires();
  }

  getFormulaires(): void {
    this.formulaireService.getAllFormulaires().subscribe((formulaires) => {
      this.formulaires = formulaires;
    });
  }

  addFormulaire(): void {
    this.formulaireService
      .addFormulaire(this.formulaire)
      .subscribe((response) => {
        this.formulaires.push(response);
        this.resetFormulaire();
      });
  }

  deleteFormulaire(formulaire: FormulaireModel): void {
    this.formulaireService.deleteFormulaire(formulaire.id).subscribe(() => {
      this.formulaires = this.formulaires.filter((f) => f.id !== formulaire.id);
    });
  }

  toggleChampVisibility(champ: ChampModel): void {
    champ.visible = !champ.visible;
  }

  resetFormulaire(): void {
    this.formulaire = {
      id: 0,
      nom: '',
      champs: [
        { nom: 'numConsultation', visible: true },
        { nom: 'titreConsultation', visible: true },
        { nom: 'objetConsultation', visible: true },
        { nom: 'conditionsParticipation', visible: true },
        { nom: 'delaiLivraison', visible: true },
      ],
    };
  }
}
