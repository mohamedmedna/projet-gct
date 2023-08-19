import { Component, OnInit, ViewChild } from '@angular/core';
import { Role } from '../models/Role';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { RoleServiceService } from '../role-service.service';

@Component({
  selector: 'app-add-role',
  templateUrl: './add-role.component.html',
  styleUrls: ['./add-role.component.css'],
})
export class AddRoleComponent implements OnInit {
  role: Role = {
    roleName: undefined,
    roleDescription: undefined,
  };
  @ViewChild('roleForm') roleForm!: NgForm;

  constructor(
    private roleService: RoleServiceService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  addRole(): void {
    if (!this.roleForm.valid) {
      alert('Veuillez ecrire une role.');
      return;
    }
    const data = {
      roleName: this.role.roleName,
      roleDescription: this.role.roleDescription,
    };
    this.roleService.addrole(data).subscribe(
      (response) => {
        console.log(response);
        this.router.navigateByUrl('/roles-list');
      },
      (error) => {
        console.error(error);
      }
    );
  }
}
