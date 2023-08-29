import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadDocumentService } from '../upload-document.service';
import { ActivatedRoute } from '@angular/router';
import { Fichier } from '../models/Fichier';
import { FormulaireModel } from '../models/FormulaireModel';
import { FormulaireService } from 'src/formulaire.service';
import { UserService } from '../authServices/user.service';

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
    private route: ActivatedRoute,
    public userService: UserService
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

  downloadDocument(iddoc: number): void {
    this.uploadService.downloadDocument(iddoc).subscribe(
      (response: any) => {
        const blob = new Blob([response], { type: 'application/octet-stream' });

        const url = window.URL.createObjectURL(blob);

        const link = document.createElement('a');
        link.href = url;
        link.download = `documentName.docx`;
        link.click();

        window.URL.revokeObjectURL(url);
      },
      (error) => {
        console.error('Error downloading document:', error);
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
