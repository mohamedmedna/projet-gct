import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../authServices/user.service';
import { NgForm } from '@angular/forms';
import { UserAuthService } from '../authServices/user-auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(
    private router: Router,
    private userservice: UserService,
    private userauth: UserAuthService
  ) {}

  ngOnInit(): void {}

  login(loginForm: NgForm) {
    this.userservice.login(loginForm.value).subscribe(
      (response: any) => {
        console.log(response.jwtToken);
        console.log(response.userauth.role);

        this.userauth.setRoles(response.userauth.role);
        this.userauth.setToken(response.jwtToken);

        const role = response.userauth.role[0].roleName;

        if (role === 'Admin') {
          this.router.navigate(['/supervisordash']);
        } else if (role === 'Supervisor') {
          this.router.navigate(['/supervisordash']);
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
