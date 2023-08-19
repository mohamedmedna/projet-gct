import { Component, OnInit, ViewChild, asNativeElements } from '@angular/core';
import { UploadDocumentService } from '../upload-document.service';
import { FormulaireService } from '../../formulaire.service';

import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { UserAuthService } from '../authServices/user-auth.service';
@Component({
  selector: 'app-adddocument',
  templateUrl: './adddocument.component.html',
  styleUrls: ['./adddocument.component.css'],
})
export class AdddocumentComponent implements OnInit {
  name!: string;
  formulairenom!: string;
  file!: File;
  nomservice!: string;

  services!: string[];
  forms!: string[];
  @ViewChild('documentForm') documentForm!: NgForm;
  serviceName = this.userAuth.getServiceName();

  ngOnInit(): void {
    this.getServices();
    this.getForms();
  }

  constructor(
    private uploaddocumentservice: UploadDocumentService,
    private formservice: FormulaireService,
    private router: Router,
    private userAuth: UserAuthService
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
    const file = event.target.files[0];
    const allowedExtensions = /(\.pdf)$/i;

    if (!allowedExtensions.exec(file.name)) {
      alert('le fichier doit etre format pdf');
      return;
    }

    this.file = file;
  }

  public onSubmit(): void {
    if (!this.documentForm.valid) {
      alert('Veuillez remplir tous les champs obligatoires.');
      return;
    }
    this.uploaddocumentservice
      .addDocument(this.name, this.nomservice, this.file, this.formulairenom)
      .subscribe(
        (response) => {
          console.log('Document ajouté avec succès');
          this.router.navigateByUrl(
            '/loadDocumentsByServiceName/' + this.serviceName
          );
        },
        (error) => {
          console.error("Erreur lors de l'ajout du document:", error);
        }
      );
  }
}
