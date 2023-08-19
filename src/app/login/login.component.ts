import { Component, OnInit, ViewChild } from '@angular/core';
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
  @ViewChild('connectForm') connectForm!: NgForm;
  constructor(
    private router: Router,
    private userservice: UserService,
    private userauth: UserAuthService
  ) {}

  ngOnInit(): void {}

  login(loginForm: NgForm) {
    if (!this.connectForm.valid) {
      alert('Veuillez remplir tous les champs obligatoires.');
      return;
    }
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
        if (!response.jwtToken || !response.userauth.role) {
          this.showLoginError();
          return;
        }

        if (role === 'Admin') {
          this.router.navigate(['/navbar']);
        } else if (role === 'Supervisor' || role === 'Worker') {
          this.router.navigate(['/loadDocumentsByServiceName/' + serviceName]);
        }
      },
      (error) => {
        console.log(error);
        this.showLoginError();
      }
    );
  }

  showLoginError() {
    alert(
      "Vos informations d'identification sont incorrectes. Veuillez réessayer."
    );
  }
}
