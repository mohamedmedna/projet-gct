import { Component, OnInit } from '@angular/core';
import { UploadDocumentService } from '../upload-document.service';
import { Fichier } from '../models/Fichier';

@Component({
  selector: 'app-documentsby-logistics',
  templateUrl: './documentsby-logistics.component.html',
  styleUrls: ['./documentsby-logistics.component.css'],
})
export class DocumentsbyLogisticsComponent implements OnInit {
  documents!: Fichier[];

  constructor(private uploaddocument: UploadDocumentService) {}

  ngOnInit(): void {
    this.loadLogisticsDocuments();
  }
  loadLogisticsDocuments() {
    this.uploaddocument.getDocumentsByService('Logistics').subscribe(
      (documents) => {
        this.documents = documents;
      },
      (error) => {
        console.error('error', error);
      }
    );
  }
}
