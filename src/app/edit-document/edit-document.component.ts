import { Component, OnInit } from '@angular/core';
import { FormulaireModel } from '../models/FormulaireModel';
import { ActivatedRoute } from '@angular/router';
import { FormulaireService } from '../../formulaire.service';

@Component({
  selector: 'app-edit-document',
  templateUrl: './edit-document.component.html',
  styleUrls: ['./edit-document.component.css'],
})
export class EditDocumentComponent implements OnInit {
  formulaireId: number | null = null;
  formulaire: FormulaireModel | null = null;

  constructor(
    private route: ActivatedRoute,
    private formulaireService: FormulaireService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id !== null) {
      this.formulaireId = parseInt(id, 10);
      if (!isNaN(this.formulaireId)) {
        this.formulaireService.getFormulaireById(this.formulaireId).subscribe(
          (formulaire) => {
            this.formulaire = formulaire;
          },
          (error) => {
            console.error(
              'Erreur lors de la récupération du formulaire:',
              error
            );
          }
        );
      } else {
        console.error("L'ID du formulaire n'est pas un nombre valide.");
      }
    } else {
      console.error("L'ID du formulaire est manquant dans l'URL.");
    }
  }
}
