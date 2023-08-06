import { Component, OnInit, ViewChild } from '@angular/core';
import { Servic } from '../models/Servic';
import { AddServiceService } from '../add-service.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

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
  @ViewChild('serviceForm') serviceForm!: NgForm;

  constructor(private addservice: AddServiceService, private router: Router) {}

  ngOnInit(): void {}

  addservic(): void {
        if (!this.serviceForm.valid) {
          alert('Veuillez ecrire une service.');
          return;
        }
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
