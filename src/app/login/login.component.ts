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
        console.log(response.userauth.service);

        this.userauth.setRoles(response.userauth.role);
        this.userauth.setToken(response.jwtToken);

        const role = response.userauth.role[0].roleName;
        const serviceName = response.userauth.servic.nomservice;
        this.userauth.setServiceName(serviceName);

        if (role === 'Admin') {
          this.router.navigate(['/admindash']);
        } else if (role === 'Supervisor' || role === 'Worker') {
          this.router.navigate(['/loadDocumentsByServiceName/' + serviceName]);
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
