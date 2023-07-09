import { Component, OnInit } from '@angular/core';
import { UploadDocumentService } from '../upload-document.service';
import { AddServiceService } from '../add-service.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-services-list',
  templateUrl: './services-list.component.html',
  styleUrls: ['./services-list.component.css'],
})
export class ServicesListComponent implements OnInit {
  constructor(private service: AddServiceService) {}

  services?: Observable<any[]>;

  ngOnInit(): void {
    this.loadServices();
  }

  loadServices(): void {
    this.services = this.service.getServices();
  }
  deleteService(id: number): void {
    this.service.deleteService(id).subscribe(
      () => {
        console.log('Document deleted successfully');
        this.loadServices();
      },
      (error) => {
        console.error('Error deleting document:', error);
      }
    );
  }
}
