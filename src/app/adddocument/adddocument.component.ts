import { Component, OnInit, asNativeElements } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UploadDocumentService } from '../upload-document.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-adddocument',
  templateUrl: './adddocument.component.html',
  styleUrls: ['./adddocument.component.css'],
})
export class AdddocumentComponent implements OnInit {
  name!: string;
  docUrl!: string;
  nomservice!: string;
  services!: string[];

  ngOnInit(): void {
    this.getServices();
  }

  constructor(
    private uploaddocumentservice: UploadDocumentService,
    private router: Router
  ) {}

  getServices(): void {
    this.uploaddocumentservice.getServices().subscribe(
      (services) => {
        this.services = services;
      },
      (error) => {
        console.error('Error retrieving services:', error);
      }
    );
  }

  addDocument(): void {
    this.uploaddocumentservice
      .addDocument(this.name, this.docUrl, this.nomservice)
      .subscribe(
        (response) => {
          console.log('Document added successfully:', response);
          this.router.navigate(['/documentsuploaded']);
        },
        error => {
          console.error('Error adding document:', error);
        }
      );
  }
}
