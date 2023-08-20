import { Component, OnInit } from '@angular/core';
import { UserService } from '../authServices/user.service';
import { Router } from '@angular/router';
import { UploadDocumentService } from '../upload-document.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  userName!: string;
  nomPrenom!: string;
  userPassword!: string;
  nomService!: string;
  roleNames: string[] = [];
  services!: string[];
  roles!: string[];
  selectedRoles: { [role: string]: boolean } = {};

  constructor(
    private userService: UserService,
    private uploaddocumentservice: UploadDocumentService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getServices();
    this.getRoles();
  }

  getServices(): void {
    this.uploaddocumentservice.getServices().subscribe(
      (services) => {
        this.services = services;
      },
      (error) => {
        console.error('Error retrieving services:', error);
      }
    );
  }

  getRoles(): void {
    this.userService.getRoles().subscribe(
      (roles) => {
        this.roles = roles;
        this.initializeSelectedRoles();
      },
      (error) => {
        console.error('Error retrieving roles:', error);
      }
    );
  }

  initializeSelectedRoles(): void {
    this.selectedRoles = {};
    for (const role of this.roles) {
      this.selectedRoles[role] = false;
    }
  }
  public onSubmit(): void {
    const selectedRoleNames = Object.keys(this.selectedRoles).filter(
      (role) => this.selectedRoles[role]
    );

    this.userService
      .addUser(
        this.userName,
        this.nomPrenom,
        this.userPassword,
        this.nomService,
        selectedRoleNames
      )
      .subscribe(
        (response) => {
          console.log('User ajouté avec succès');
          this.router.navigateByUrl('/users-list');
        },
        (error) => {
          console.error("Erreur lors de l'ajout du User:", error);
        }
      );
  }


}
