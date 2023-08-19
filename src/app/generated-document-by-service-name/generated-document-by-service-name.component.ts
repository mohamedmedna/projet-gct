import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadDocumentService } from '../upload-document.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-generated-document-by-service-name',
  templateUrl: './generated-document-by-service-name.component.html',
  styleUrls: ['./generated-document-by-service-name.component.css'],
})
export class GeneratedDocumentByServiceNameComponent implements OnInit {
  documents?: Observable<any[]>;
  services!: string[];
  _serviceName?: any;

  constructor(
    private uploadService: UploadDocumentService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const serviceName = this.route.snapshot.paramMap.get('serviceName');
    if (serviceName) {
      this.loadGeneratedDocumentsByServiceName(serviceName);
    }
    this.loadServices();
    this._serviceName = serviceName;
  }

  loadGeneratedDocumentsByServiceName(_serviceName: string): void {
    this.documents =
      this.uploadService.getDocumentsGeneratedByService(_serviceName);
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
  deleteDocument(id: number): void {
    this.uploadService.deleteGeneratedDocument(id).subscribe(
      () => {
        console.log('Document deleted successfully');
        const serviceName = this.route.snapshot.paramMap.get('serviceName');
        if (serviceName) {
          this.loadGeneratedDocumentsByServiceName(serviceName);
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
}