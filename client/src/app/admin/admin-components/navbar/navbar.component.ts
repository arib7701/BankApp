import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  loggedIn: Boolean = false;

  constructor(private route: Router) { }

  ngOnInit() {
    const logged = localStorage.getItem('loggedInAdmin');
    if (logged === 'true') {
      this.loggedIn = true;
    }
  }

  logout() {
    localStorage.setItem('loggedInAdmin', 'false');
    this.loggedIn = false;
    this.route.navigate(['/admin']);
  }

}
