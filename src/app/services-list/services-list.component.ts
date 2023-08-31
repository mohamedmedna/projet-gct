import { Component, OnInit } from '@angular/core';
import { UploadDocumentService } from '../upload-document.service';
import { AddServiceService } from '../add-service.service';
import { Observable } from 'rxjs';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-services-list',
  templateUrl: './services-list.component.html',
  styleUrls: ['./services-list.component.css'],
})
export class ServicesListComponent implements OnInit {
  constructor(private service: AddServiceService, private router: Router) {}

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
        console.log('Role deleted successfully');
        this.loadServices();
      },
      (error) => {
        console.error('Error deleting role:', error);
      }
    );
  }
  updateServic(id: any) {
    this.router.navigate(['/updateServic', id]);
  }
}
