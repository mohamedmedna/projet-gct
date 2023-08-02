// edit-document.component.ts

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormulaireModel } from '../models/FormulaireModel';
import { UploadDocumentService } from '../upload-document.service';
import { FormulaireService } from 'src/formulaire.service';

@Component({
  selector: 'app-edit-document',
  templateUrl: './edit-document.component.html',
  styleUrls: ['./edit-document.component.css'],
})
export class EditDocumentComponent implements OnInit {
  documentId: number | null = null;
  formulaire: FormulaireModel | null = null;

  constructor(
    private route: ActivatedRoute,
    private documentService: UploadDocumentService,
    private formulaireService: FormulaireService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id !== null) {
      this.documentId = parseInt(id, 10);
      if (!isNaN(this.documentId)) {
        this.formulaireService.getFormulaireById(this.documentId).subscribe(
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
        console.error("L'ID du document n'est pas un nombre valide.");
      }
    } else {
      console.error("L'ID du document est manquant dans l'URL.");
    }
  }

  onSubmit(): void {
    if (this.documentId && this.formulaire) {
      this.documentService
        .generateUpdatedPdf(this.documentId, this.formulaire)
        .subscribe(
          (response) => {
            if (response && response.body) {
              this.downloadFile(response.body);
            } else {
              console.error('Error: Response body is null.');
            }
          },
          (error) => {
            console.error('Error generating updated PDF:', error);
          }
        );
    }
  }

  private downloadFile(data: ArrayBuffer) {
    const blob = new Blob([data], { type: 'application/pdf' });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = 'updated-document.pdf';
    link.click();
  }
}
