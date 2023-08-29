import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadDocumentService } from '../upload-document.service';
import { UserService } from '../authServices/user.service';

@Component({
  selector: 'app-generated-document',
  templateUrl: './generated-document.component.html',
  styleUrls: ['./generated-document.component.css'],
})
export class GeneratedDocumentComponent implements OnInit {
  documents?: Observable<any[]>;
  services!: string[];
  constructor(
    private uploadService: UploadDocumentService,
    public userService: UserService
  ) {}

  ngOnInit(): void {
    this.getAll();
    this.loadServices();
  }
  getAll() {
    this.documents = this.uploadService.getGeneratedDocuments();
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

  loadGeneratedDocumentsByService(serviceName: string): void {
    this.documents =
      this.uploadService.getDocumentsGeneratedByService(serviceName);
  }

  confirmDelete(id: number): void {
    const confirmation = confirm(
      'Êtes-vous sûr de vouloir supprimer ce document ?'
    );

    if (confirmation) {
      this.deleteGeneratedDocument(id);
    }
  }

  deleteGeneratedDocument(id: number): void {
    this.uploadService.deleteGeneratedDocument(id).subscribe(
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

  downloadDocument(iddoc: number): void {
    this.uploadService.downloadGeneratedDocument(iddoc).subscribe(
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
}
