import { Component, OnInit } from '@angular/core';
import { UserService } from '../authServices/user.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent implements OnInit {
  constructor(private userService: UserService) {}
  users?: Observable<any[]>;

  ngOnInit(): void {
    this.getAll();
  }
  getAll() {
    this.users = this.userService.getUsers();
  }

  deleteUser(id: number): void {
    this.userService.deleteUser(id).subscribe(
      () => {
        console.log('User deleted successfully');
        this.getAll();
      },
      (error) => {
        console.error('Error deleting user:', error);
      }
    );
  }
}
