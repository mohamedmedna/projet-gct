import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormulaireModel } from '../models/FormulaireModel';
import { UploadDocumentService } from '../upload-document.service';
import { FormulaireService } from 'src/formulaire.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-edit-document',
  templateUrl: './edit-document.component.html',
  styleUrls: ['./edit-document.component.css'],
})
export class EditDocumentComponent implements OnInit {
  documentId: number | null = null;
  formulaire: FormulaireModel | null = null;
  formulaireId: number | null = null;
  documentname!: string | null;
  downloadingInProgress = false;
  @ViewChild('editDocumentForm') editDocumentForm!: NgForm;

  constructor(
    private route: ActivatedRoute,
    private documentService: UploadDocumentService,
    private formulaireService: FormulaireService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    const documentIdParam = this.route.snapshot.paramMap.get('iddoc');
    this.documentname = this.route.snapshot.paramMap.get('name');
    if (documentIdParam !== null) {
      this.documentId = parseInt(documentIdParam, 10);
    }

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
        console.error("L'ID du document n'est pas valide.");
      }
    } else {
      console.error("L'ID du document n'est pas valide.");
    }
  }

  onSubmit(editDocumentForm: NgForm): void {
    if (this.documentId && this.formulaire) {
      this.downloadingInProgress = true;
      /*window.alert('Téléchargement en cours...');*/
      this.documentService
        .generateUpdatedPdf(this.documentId, this.formulaire)
        .subscribe(
          (response) => {
            const blob = new Blob([response], { type: 'application/pdf' });

            const url = URL.createObjectURL(blob);

            const anchor = document.createElement('a');
            anchor.href = url;
            anchor.download = `${this.documentname}${this.documentId}_modified.pdf`;
            anchor.click();

            URL.revokeObjectURL(url);
            anchor.remove();
            editDocumentForm.resetForm();
            this.downloadingInProgress = false;
          },
          (error) => {
            console.error('Error', error);
            this.downloadingInProgress = false;
          }
        );
    } else {
      console.error('error');
      this.downloadingInProgress = false;
    }
  }
}
