import { Component, OnInit } from '@angular/core';
import { Servic } from '../models/Servic';
import { AddServiceService } from '../add-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-addservice',
  templateUrl: './addservice.component.html',
  styleUrls: ['./addservice.component.css'],
})
export class AddserviceComponent implements OnInit {
  service: Servic = {
    idservice: undefined,
    nomservice: '',
  };

  constructor(private addservice: AddServiceService, private router: Router) {}

  ngOnInit(): void {}

  addservic(): void {
    const data = {
      nomservice: this.service.nomservice,
    };
    this.addservice.addservice(data).subscribe(
      (response) => {
        console.log(response);
        this.router.navigateByUrl('/services-list');
      },
      (error) => {
        console.error(error);
      }
    );
  }
}