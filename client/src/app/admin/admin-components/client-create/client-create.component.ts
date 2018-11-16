import { Component, OnInit, OnDestroy } from '@angular/core';
import { ClientService } from '../../../service/client.service';
import { Client } from '../../../model/client';
import { Subscription } from 'rxjs';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { FlashMessagesService } from 'angular2-flash-messages';

@Component({
  selector: 'app-client-create',
  templateUrl: './client-create.component.html',
  styleUrls: ['./client-create.component.css']
})
export class ClientCreateComponent implements OnInit, OnDestroy {
  newClient: Client = new Client();
  subscriptionAddClient: Subscription;
  clientForm: FormGroup;

  constructor(private clientService: ClientService, private flashMessageService: FlashMessagesService) { }

  ngOnInit() {
    this.createForm();
  }

  createForm() {
    this.clientForm = new FormGroup({
      firstname: new FormControl('', [Validators.required]),
      lastname: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      phone: new FormControl('', [Validators.required, Validators.minLength(10), Validators.maxLength(10)])
    });
  }

  onSubmit() {

    this.newClient.firstname = this.clientForm.value.firstname;
    this.newClient.lastname = this.clientForm.value.lastname;
    this.newClient.email = this.clientForm.value.email;
    this.newClient.phone = this.clientForm.value.phone;

    this.subscriptionAddClient = this.clientService
    .addClient(this.newClient)
    .subscribe( result => {
      console.log('Return adding new client success ', result);
      this.flashMessageService.show('New Client created successfully', {cssClass: 'alert-success', timeout: 4000});
    }, error => {
      console.log('Error adding new client ', error);
      this.flashMessageService.show('Error creating new client. Please try again later.', {cssClass: 'alert-danger', timeout: 4000});
    });
  }

  ngOnDestroy() {
    if (this.subscriptionAddClient !== undefined) {
      this.subscriptionAddClient.unsubscribe();
    }
  }


  get firstname() {
    return this.clientForm.get('firstname');
  }
  get lastname() {
    return this.clientForm.get('lastname');
  }
  get email() {
    return this.clientForm.get('email');
  }
  get phone() {
    return this.clientForm.get('phone');
  }

}
