import { Component, OnInit, asNativeElements } from '@angular/core';
import { UploadDocumentService } from '../upload-document.service';
import { FormulaireService } from '../../formulaire.service';

import { Router } from '@angular/router';
@Component({
  selector: 'app-adddocument',
  templateUrl: './adddocument.component.html',
  styleUrls: ['./adddocument.component.css'],
})
export class AdddocumentComponent implements OnInit {
  name!: string;
  form!: string;
  file!: File;
  nomservice!: string;
  services!: string[];
  forms!: string[];

  ngOnInit(): void {
    this.getServices();
  }

  constructor(
    private uploaddocumentservice: UploadDocumentService,
    private formservice: FormulaireService,
    private router: Router
  ) {}

  getServices(): void {
    this.uploaddocumentservice.getServices().subscribe(
      (services) => {
        this.services = services;
      },
      (error) => {
        console.error('Error retrieving services:', error);
      }
    );
  }

  getForms(): void {
    this.formservice.getForms().subscribe(
      (forms) => {
        this.forms = forms;
      },
      (error) => {
        console.error('Error retrieving Forms:', error);
      }
    );
  }

  public onFileChange(event: any): void {
    this.file = event.target.files[0];
  }

  public onSubmit(): void {
    this.uploaddocumentservice
      .addDocument(this.name, this.nomservice, this.file, this.form)
      .subscribe(
        (response) => {
          console.log('Document ajouté avec succès');
          this.router.navigateByUrl('/documentsuploaded');
        },
        (error) => {
          console.error("Erreur lors de l'ajout du document:", error);
        }
      );
  }
}
