import { Component, OnInit } from '@angular/core';
import { User } from '../models/User';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../authServices/user.service';
import { UploadDocumentService } from '../upload-document.service';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css'],
})
export class UpdateUserComponent implements OnInit {
  id?: any;
  user: User = {
    userName: '',
    nomPrenom: '',
    userPassword: '',
    nomService: '',
    roleNames: [],
  };
  services!: string[];
  roles!: string[];
  selectedRoles: { [role: string]: boolean } = {};

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private uploaddocumentservice: UploadDocumentService
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getServices();
    this.getRoles();
    this.getUserById(this.id);
  }

  getUserById(id: any): void {
    this.userService.getUserById(id).subscribe(
      (userData: User) => {
        this.user = userData;
        this.initializeSelectedRoles();
        if (this.user.roleNames) {
          for (const role of this.user.roleNames) {
            this.selectedRoles[role] = true;
          }
        }
      },
      (error) => {
        console.error('Error retrieving user data:', error);
      }
    );
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
  updateUser(): void {
    this.userService.update(this.id, this.user).subscribe(
      (data) => {
        console.log(data);
        this.user = new User();
        this.gotoList();
      },
      (error) => console.log(error)
    );
  }

  onSubmit() {
    this.updateUser();
  }
  gotoList() {
    this.router.navigate(['/users-list']);
  }
}
