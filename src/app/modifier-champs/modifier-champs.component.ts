import { Component, OnInit } from '@angular/core';
import { FormulaireService } from '../../formulaire.service';
import { FormulaireModel } from '../models/FormulaireModel';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-modifier-champs',
  templateUrl: './modifier-champs.component.html',
  styleUrls: ['./modifier-champs.component.css'],
})
export class ModifierChampsComponent implements OnInit {
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
    delaiLivraison: '',
    delaiLivraisonestVisible: true,
    dureeGarantie: '',
    dureeGarantieestVisible: true,
    modifier: false,
  };
  formulaireid!: number;

  constructor(
    private formulaireService: FormulaireService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    this.getFormulaireById(id);
  }

  getFormulaireById(id: number): void {
    this.formulaireService.getFormulaireById(id).subscribe(
      (response) => {
        this.formulaire = response;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  updateChampVisibility(champ: string): void {
    let visible = false;

    switch (champ) {
      case 'numConsultation':
        visible = this.formulaire.numConsultationestVisible;
        break;
      case 'titreConsultation':
        visible = this.formulaire.titreConsultationestVisible;
        break;
      case 'objetConsultation':
        visible = this.formulaire.objetConsultationestVisible;
        break;
      case 'conditionsParticipation':
        visible = this.formulaire.conditionsParticipationestVisible;
        break;
      case 'delaiLivraison':
        visible = this.formulaire.delaiLivraisonestVisible;
        break;
      case 'dureeGarantie':
        visible = this.formulaire.dureeGarantieestVisible;
        break;
    }

    this.formulaireService
      .updateChampVisibility(this.formulaire.id, champ, visible)
      .subscribe(
        (response) => {
          console.log(response);
        },
        (error) => {
          console.log(error);
        }
      );
  }

  saveChanges(): void {
    this.router.navigate(['/formulaire']);
  }
}
