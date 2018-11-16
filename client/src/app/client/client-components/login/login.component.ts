import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { ClientService } from '../../../service/client.service';
import { Subscription } from 'rxjs';
import { Client } from '../../../model/client';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { FlashMessagesService } from 'angular2-flash-messages';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  subscriptionClient: Subscription;
  client: Client;
  loginForm: FormGroup;

  constructor(
    private router: Router,
    private clientService: ClientService,
    private flashMessagesService: FlashMessagesService) { }

  ngOnInit() {
    this.createForm();
  }

  createForm() {
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required])
    });
  }

  onSubmit() {

    this.subscriptionClient = this.clientService
    .getClientByEmail(this.loginForm.value.email)
    .subscribe(result => {
      this.client = result;
      localStorage.setItem('loggedIn', 'true');
      localStorage.setItem('clientId', `${this.client.id}`);
      localStorage.setItem('firstname', `${this.client.firstname}`);
      localStorage.setItem('lastname', `${this.client.lastname}`);
      location.reload();
      this.router.navigate(['/account/list/', this.client.id]);
    }, error => {
      console.log('Error getting client ', error);

      this.flashMessagesService.show(
        'Error login in. Please verify your email & password.',
        { cssClass: 'alert-danger',
          timeout: 4000 });
    });
  }

  ngOnDestroy() {
    if (this.subscriptionClient !== undefined) {
      this.subscriptionClient.unsubscribe();
    }
  }


  get email() {
    return this.loginForm.get('email');
  }
  get password() {
    return this.loginForm.get('password');
  }

}
