import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from '../../../service/account.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Account } from '../../../model/account';
import { Subscription } from 'rxjs';
import { FlashMessagesService } from 'angular2-flash-messages';

@Component({
  selector: 'app-account-create',
  templateUrl: './account-create.component.html',
  styleUrls: ['./account-create.component.css']
})
export class AccountCreateComponent implements OnInit, OnDestroy {
  type: String = 'saving';
  clientId: number;
  accountForm: FormGroup;
  newAccount: Account = new Account();
  subscriptionAddAccount: Subscription;

  constructor(
    private route: ActivatedRoute,
    private accountService: AccountService,
    private router: Router,
    private flashMessageService: FlashMessagesService) { }

  ngOnInit() {
      this.type = this.route.snapshot.paramMap.get('type');
      this.clientId = +this.route.snapshot.paramMap.get('id');
      this.createForm(this.type);
  }

  createForm(type: String) {

    if (type === 'spending') {
      this.accountForm = new FormGroup({
        name: new FormControl('', [Validators.required]),
        amount: new FormControl('', [Validators.required]),
        credit: new FormControl('', [Validators.required, Validators.max(9999)])
      });

    } else if (type === 'saving') {

      this.accountForm = new FormGroup({
        name: new FormControl('', [Validators.required]),
        amount: new FormControl('', [Validators.required]),
        minimum: new FormControl('', [Validators.required, Validators.max(99)]),
        interest: new FormControl('', [Validators.required, Validators.max(99)])
      });
    }
  }

  onSubmit(type: String) {

    this.newAccount.name = this.accountForm.value.name;
    this.newAccount.amount = this.accountForm.value.amount;
    this.newAccount.clientId = this.clientId;

    if (type === 'spending') {
      this.newAccount.type = 'spending';
      this.newAccount.credit = this.accountForm.value.credit;
    } else if (type === 'saving') {
      this.newAccount.type = 'saving';
      this.newAccount.minimum = this.accountForm.value.minimum;
      this.newAccount.interest = this.accountForm.value.interest;
    }

    this.subscriptionAddAccount = this.accountService
    .addAccount(type, this.newAccount)
    .subscribe(result => {
      console.log('Account added successfully ', result);
      this.flashMessageService.show('New Account created successfully', {cssClass: 'alert-success', timeout: 4000});
    }, error => {
      console.log('Error adding spending account ', error);
      this.flashMessageService.show('Error creating new account. Please try again later.', {cssClass: 'alert-danger', timeout: 4000});
    });
  }

  get name() {
    return this.accountForm.get('name');
  }
  get amount() {
    return this.accountForm.get('amount');
  }
  get credit() {
    return this.accountForm.get('credit');
  }
  get minimum() {
    return this.accountForm.get('minimum');
  }
  get interest() {
    return this.accountForm.get('interest');
  }

  ngOnDestroy() {
    if (this.subscriptionAddAccount !== undefined) {
      this.subscriptionAddAccount.unsubscribe();
    }
  }

}
