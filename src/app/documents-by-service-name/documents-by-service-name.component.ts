import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadDocumentService } from '../upload-document.service';
import { ActivatedRoute } from '@angular/router';
import { Fichier } from '../models/Fichier';
import { FormulaireModel } from '../models/FormulaireModel';
import { FormulaireService } from 'src/formulaire.service';

@Component({
  selector: 'app-documents-by-service-name',
  templateUrl: './documents-by-service-name.component.html',
  styleUrls: ['./documents-by-service-name.component.css'],
})
export class DocumentsByServiceNameComponent implements OnInit {
  documents?: Observable<any[]>;
  formulaire!: FormulaireModel;
  services!: string[];
  _serviceName?: any;

  constructor(
    private uploadService: UploadDocumentService,
    private formulaireService: FormulaireService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const serviceName = this.route.snapshot.paramMap.get('serviceName');
    if (serviceName) {
      this.loadDocumentsByServiceName(serviceName);
    }
    this.loadServices();
    this._serviceName = serviceName;
  }

  loadDocumentsByServiceName(_serviceName: string): void {
    this.documents = this.uploadService.getDocumentsByService(_serviceName);
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
        const serviceName = this.route.snapshot.paramMap.get('serviceName');
        if (serviceName) {
          this.loadDocumentsByServiceName(serviceName);
        }
      },
      (error) => {
        console.error('Error deleting document:', error);
      }
    );
  }

  getDownloadUrl(iddoc: number): string {
    return this.uploadService.getDownloadUrl(iddoc);
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
