import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  loggedIn: Boolean = false;
  clientId: number;
  firstname: String;
  lastname: String;

  constructor(private route: Router) { }

  ngOnInit() {
    const logged = localStorage.getItem('loggedIn');
    if (logged === 'true') {
      this.loggedIn = true;
      this.clientId = +localStorage.getItem('clientId');
      this.firstname = localStorage.getItem('firstname');
      this.lastname = localStorage.getItem('lastname');
    }
  }

  logout() {
    localStorage.setItem('loggedIn', 'false');
    localStorage.setItem('clientId', ``);
    localStorage.setItem('firstname', `Client`);
    localStorage.setItem('lastname', `Name`);
    this.loggedIn = false;
    this.route.navigate(['/login']);
  }
}
