import { Component, OnInit } from '@angular/core';
import { RoleServiceService } from '../role-service.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-roles-list',
  templateUrl: './roles-list.component.html',
  styleUrls: ['./roles-list.component.css'],
})
export class RolesListComponent implements OnInit {
  constructor(private service: RoleServiceService) {}

  roles?: Observable<any[]>;

  ngOnInit(): void {
    this.loadRoles();
  }

  loadRoles(): void {
    this.roles = this.service.getRoless();
  }
  deleteRole(id: number): void {
    this.service.deleteRole(id).subscribe(
      () => {
        console.log('Role deleted successfully');
        this.loadRoles();
      },
      (error) => {
        console.error('Error deleting document:', error);
      }
    );
  }
}
