import { Component, OnInit, asNativeElements } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UploadDocumentService } from '../upload-document.service';
@Component({
  selector: 'app-adddocument',
  templateUrl: './adddocument.component.html',
  styleUrls: ['./adddocument.component.css'],
})
export class AdddocumentComponent implements OnInit {
  name!: string;
  docUrl!: string;
  idService!: number | null;
  ngOnInit(): void {}

  constructor(private uploaddocumentservice: UploadDocumentService) {}

  addDocument(): void {
    this.uploaddocumentservice
      .addDocument(this.name as string, this.docUrl as string, this.idService as number)
      .subscribe(
        (response) => {
          console.log('Document added successfully:', response);
          this.name = '';
          this.docUrl = '';
          this.idService = null;
        },
        (error) => {
          console.error('Error adding document:', error);
        }
      );
  }
}
