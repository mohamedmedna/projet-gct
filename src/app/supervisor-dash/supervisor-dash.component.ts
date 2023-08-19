import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserAuthService } from '../authServices/user-auth.service';
import { UserService } from '../authServices/user.service';

@Component({
  selector: 'app-supervisor-dash',
  templateUrl: './supervisor-dash.component.html',
  styleUrls: ['./supervisor-dash.component.css'],
})
export class SupervisorDashComponent implements OnInit {
  constructor(private router: Router, private userAuth: UserAuthService,public userService:UserService) {}

  serviceName = this.userAuth.getServiceName();
  ngOnInit(): void {}
  logout() {
    this.userAuth.clear();
    this.router.navigateByUrl('/login');
  }

  isLoggedIn() {
    return this.userAuth.isLoggedIn();
  }
}
