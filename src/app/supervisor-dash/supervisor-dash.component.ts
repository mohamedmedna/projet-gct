import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-supervisor-dash',
  templateUrl: './supervisor-dash.component.html',
  styleUrls: ['./supervisor-dash.component.css'],
})
export class SupervisorDashComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {

  }

}
