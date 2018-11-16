import { Component, OnInit, OnDestroy } from '@angular/core';
import { ClientService } from 'src/app/service/client.service';
import { ActivatedRoute } from '@angular/router';
import { Client } from 'src/app/model/client';
import { Subscription } from 'rxjs';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { FlashMessagesService } from 'angular2-flash-messages';


@Component({
  selector: 'app-profil-page-edit',
  templateUrl: './profil-page-edit.component.html',
  styleUrls: ['./profil-page-edit.component.css']
})
export class ProfilPageEditComponent implements OnInit, OnDestroy {
  id: number;
  client: Client;
  subscriptionUpdateClient: Subscription;
  subscriptionGetClient: Subscription;
  editClientForm: FormGroup;

  constructor(
    private clientService: ClientService,
    private route: ActivatedRoute,
    private flashMessageService: FlashMessagesService) { }

  ngOnInit() {

    this.createForm();

    this.id = +this.route.snapshot.paramMap.get('id');

    this.subscriptionGetClient = this.clientService
    .getClientById(this.id)
    .subscribe(result => {
      this.client = result;

      // Set value of Form
      this.editClientForm.controls['firstname'].setValue(this.client.firstname);
      this.editClientForm.controls['lastname'].setValue(this.client.lastname);
      this.editClientForm.controls['email'].setValue(this.client.email);
      this.editClientForm.controls['phone'].setValue(this.client.phone);
    }, error => {
      console.log('Error getting client data', error);
    });
  }

  createForm() {
    this.editClientForm = new FormGroup({
      firstname: new FormControl({value: '', disabled: true}, [Validators.required]),
      lastname: new FormControl({value: '', disabled: true}, [Validators.required]),
      email: new FormControl({value: ''}, [Validators.required, Validators.email]),
      phone: new FormControl({value: ''}, [Validators.required, Validators.minLength(10), Validators.maxLength(10)])
    });
  }

  onSubmit() {

    this.client.firstname = this.editClientForm.value.firstname;
    this.client.lastname = this.editClientForm.value.lastname;
    this.client.email = this.editClientForm.value.email;
    this.client.phone = this.editClientForm.value.phone;

    this.subscriptionUpdateClient = this.clientService
    .updateClient(this.id, this.client)
    .subscribe(result => {
        console.log('Client updated successfuly', result);
        this.flashMessageService.show('Your information has been updated successfully', {cssClass: 'alert-success', timeout: 4000});
    }, error => {
      console.log('Error update client ', error);
      this.flashMessageService.show('Error updating your information. Please try again later.', {cssClass: 'alert-danger', timeout: 4000});
    });
  }

  ngOnDestroy() {
    if (this.subscriptionUpdateClient !== undefined) {
      this.subscriptionUpdateClient.unsubscribe();
    }
  }


  get firstname() {
    return this.editClientForm.get('firstname');
  }
  get lastname() {
    return this.editClientForm.get('lastname');
  }
  get email() {
    return this.editClientForm.get('email');
  }
  get phone() {
    return this.editClientForm.get('phone');
  }

}
