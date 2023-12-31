import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadDocumentService } from '../upload-document.service';
import { FormulaireModel } from '../models/FormulaireModel';
import { FormulaireService } from 'src/formulaire.service';
import { UserService } from '../authServices/user.service';

@Component({
  selector: 'app-documents-uploaded',
  templateUrl: './documents-uploaded.component.html',
  styleUrls: ['./documents-uploaded.component.css'],
})
export class DocumentsUploadedComponent implements OnInit {
  documents?: Observable<any[]>;
  services!: string[];
  formulaire!: FormulaireModel;
  constructor(
    private uploadService: UploadDocumentService,
    private formulaireService: FormulaireService,
    public userService: UserService
  ) {}

  ngOnInit(): void {
    this.getAll();
    this.loadServices();
  }
  getAll() {
    this.documents = this.uploadService.getdocuments();
  }

  loadServices(): void {
    this.uploadService.getServices().subscribe(
      (services) => {
        this.services = services;
      },
      (error) => {
        console.error('Error loading services:', error);
      }
    );
  }

  loadDocumentsByService(serviceName: string): void {
    this.documents = this.uploadService.getDocumentsByService(serviceName);
  }

  confirmDelete(id: number): void {
    const confirmation = confirm(
      'Êtes-vous sûr de vouloir supprimer ce document ?'
    );

    if (confirmation) {
      this.deleteDocument(id);
    }
  }

  deleteDocument(id: number): void {
    this.uploadService.deleteDocument(id).subscribe(
      () => {
        console.log('Document deleted successfully');
        this.getAll();
      },
      (error) => {
        console.error('Error deleting document:', error);
      }
    );
  }
  getServices(): void {
    this.uploadService.getServices().subscribe(
      (services) => {
        this.services = services;
      },
      (error) => {
        console.error('Error retrieving services:', error);
      }
    );
  }


  getFormulaireDetails(id: number): void {
    this.formulaireService.getFormulaireById(id).subscribe(
      (formulaire) => {
        this.formulaire = formulaire;
      },
      (error) => {
        console.error('Error retrieving formulaire details:', error);
      }
    );
  }
}
