import { Component, OnInit } from '@angular/core';
import { Fichier } from '../models/Fichier';
import { UploadDocumentService } from '../upload-document.service';

@Component({
  selector: 'app-documentsby-marketing',
  templateUrl: './documentsby-marketing.component.html',
  styleUrls: ['./documentsby-marketing.component.css'],
})
export class DocumentsbyMarketingComponent implements OnInit {
  documents!: Fichier[];

  constructor(private uploaddocument: UploadDocumentService) {}

  ngOnInit(): void {
    this.loadMarketingDocuments();
  }
  loadMarketingDocuments() {
    this.uploaddocument.getDocumentsByService('Marketing').subscribe(
      (documents) => {
        this.documents = documents;
      },
      (error) => {
        console.error('error', error);
      }
    );
  }
}
