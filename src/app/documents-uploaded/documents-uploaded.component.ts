import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Fichier } from '../models/Fichier';
import { UploadDocumentService } from '../upload-document.service';

@Component({
  selector: 'app-documents-uploaded',
  templateUrl: './documents-uploaded.component.html',
  styleUrls: ['./documents-uploaded.component.css'],
})
export class DocumentsUploadedComponent implements OnInit {
  documents?: Observable<any[]>;
  services!: string[];
  constructor(private uploadService: UploadDocumentService) {}

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
  getDownloadUrl(iddoc: number): string {
    return this.uploadService.getDownloadUrl(iddoc);
  }

  editDocument(form: string): void {
    switch (form) {
      case 'Form1':
        window.location.href = 'form1';
        break;

      case 'Form2':
        window.location.href = 'form2';
        break;

      case 'Form3':
        window.location.href = 'form3';
        break;

      default:
        window.location.href = 'documentsuploaded';
        break;
    }
  }
}
