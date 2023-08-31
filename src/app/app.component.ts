import { Component } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'projetgct';
  showNavbar: boolean = true;

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {
    router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        this.showNavbar = !(activatedRoute.snapshot.url[0]?.path === 'login');
      });
  }
}
