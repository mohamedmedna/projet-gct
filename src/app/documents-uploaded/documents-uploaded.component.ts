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
  }
  getAll() {
    this.documents = this.uploadService.getdocuments();
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
}
