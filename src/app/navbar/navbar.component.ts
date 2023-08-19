import { Component, OnInit } from '@angular/core';
import { UserAuthService } from '../authServices/user-auth.service';
import { UserService } from '../authServices/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  constructor(
    private router: Router,
    private userAuth: UserAuthService,
    public userService: UserService
  ) {}

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
