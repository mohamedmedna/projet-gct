import { Component, OnInit } from '@angular/core';
import { AddServiceService } from '../add-service.service';
import { Servic } from '../models/Servic';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-service',
  templateUrl: './update-service.component.html',
  styleUrls: ['./update-service.component.css'],
})
export class UpdateServiceComponent implements OnInit {
  id?: any;
  servic: Servic = {
    nomservice: '',
  };
  constructor(
    private service: AddServiceService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getServicById(this.id);
  }

  getServicById(id: any): void {
    this.service.getServicById(id).subscribe(
      (servicData: Servic) => {
        this.servic = servicData;
      },
      (error) => {
        console.error('Error retrieving servic data:', error);
      }
    );
  }

  updateService(): void {
    this.service.updateservice(this.id, this.servic).subscribe(
      (data) => {
        console.log(data);
        this.servic = new Servic();
        this.gotoList();
      },
      (error) => console.log(error)
    );
  }
  onSubmit() {
    this.updateService();
  }
  gotoList() {
    this.router.navigate(['/services-list']);
  }
}
