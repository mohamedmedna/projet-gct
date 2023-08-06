import { Component, OnInit, ViewChild } from '@angular/core';
import { FormulaireModel } from '../models/FormulaireModel';
import { FormulaireService } from '../../formulaire.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
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
    numConsultation: '',
    numConsultationestVisible: true,
    titreConsultation: '',
    titreConsultationestVisible: true,
    objetConsultation: '',
    objetConsultationestVisible: true,
    conditionsParticipation: '',
    conditionsParticipationestVisible: true,
    delaiLivraison: new Date(),
    delaiLivraisonestVisible: true,
    dureeGarantie: new Date(),
    dureeGarantieestVisible: true,
    modifier: false,
  };
  @ViewChild('formulaireForm') formulaireForm!: NgForm;

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

  addFormulaire(): void {
    if (!this.formulaireForm.valid) {
      alert('Veuillez donner un nom du formulaire.');
      return;
    }
    this.formulaireService
      .addFormulaire(this.formulaire)
      .subscribe((response: any) => {
        this.formulaires.push(response);
      });
  }

  toggleModifier(formulaire: FormulaireModel): void {
    formulaire.modifier = !formulaire.modifier;
    this.router.navigate(['/modifiers-champs', formulaire.id]);
  }

  deleteFormulaire(formulaire: FormulaireModel): void {
    this.formulaireService.deleteFormulaire(formulaire.id).subscribe(() => {
      this.formulaires = this.formulaires.filter((f) => f.id !== formulaire.id);
    });
  }
}
