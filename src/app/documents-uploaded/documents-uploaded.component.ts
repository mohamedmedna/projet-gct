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
  constructor(private uploadService: UploadDocumentService) {}

  ngOnInit(): void {
    this.getAll();
  }
  getAll() {
    this.documents = this.uploadService.getdocuments();
  }
}
