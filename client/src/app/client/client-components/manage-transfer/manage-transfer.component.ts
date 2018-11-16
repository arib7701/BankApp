import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Account } from './../../../model/account';
import { Subscription } from 'rxjs';
import { AccountService } from 'src/app/service/account.service';
import { Client } from 'src/app/model/client';
import { ClientService } from 'src/app/service/client.service';
import { FlashMessagesService } from 'angular2-flash-messages';

@Component({
  selector: 'app-manage-transfer',
  templateUrl: './manage-transfer.component.html',
  styleUrls: ['./manage-transfer.component.css']
})
export class ManageTransferComponent implements OnInit, OnDestroy {

  @Input()
  typeTransfer: String;

  @Input()
  clientId: number;

  transferForm: FormGroup;
  accounts: Account[] = new Array<Account>();
  client: Client;
  toOtherAccount: Account;
  subscriptionSpendingAccounts: Subscription;
  subscriptionSavingAccounts: Subscription;
  subscriptionUpdateAcc: Subscription;
  subscriptionAcc: Subscription;
  subscriptionClient: Subscription;

  constructor(
    private accountService: AccountService,
    private clientService: ClientService,
    private flashMessageService: FlashMessagesService) { }

  ngOnInit() {
    this.createForm();
    this.getSpendingAcc();
  }

  getSpendingAcc() {
    this.subscriptionSpendingAccounts = this.accountService
    .getAllAccountByClientId('spending', this.clientId)
    .subscribe(result => {
      const acc = result as Account;
      acc.type = 'spending';
      this.accounts.push(acc);
      this.getSavingsAcc();
    }, error => {
      console.log('Error getting spending account ', error);
    });
  }

  getSavingsAcc() {
    this.subscriptionSavingAccounts = this.accountService
    .getAllAccountByClientId('saving', this.clientId)
    .subscribe(result => {

      for (const res of result) {
        res.type = 'saving';
        this.accounts.push(res);
      }

    }, error => {
      console.log('Error getting saving account ', error);
    });
  }

  createForm() {
      this.transferForm = new FormGroup({
        fromAccount: new FormControl('', [Validators.required]),
        toAccount: new FormControl('', [Validators.required]),
        typeToAccount: new FormControl('spending', [Validators.required]),
        amount: new FormControl('', [Validators.required]),
        firstname: new FormControl('-', [Validators.required]),
        lastname: new FormControl('-', [Validators.required])
      });
  }

  onSubmit() {

    event.preventDefault();

    // Values Form
    const money = this.transferForm.value.amount;
    const fromId = this.transferForm.value.fromAccount;
    const toId = this.transferForm.value.toAccount;
    const typeTo = this.transferForm.value.typeToAccount;
    const first = this.transferForm.value.firstname;
    const last = this.transferForm.value.lastname;

    const fromAcc: Account = this.accounts[fromId];

    // Check if enough money on account
    if (fromAcc.amount < money) {
      console.log('Not enough money on the account for the transfer');

    } else {

      if (this.typeTransfer === 'myAccounts') {

        const toAcc: Account = this.accounts[toId];

        // Change amounts of the two acc
        fromAcc.amount = fromAcc.amount - money;
        toAcc.amount = toAcc.amount + money;

        // Update the two acc
        this.updateAccount(fromAcc.type, fromAcc);
        this.updateAccount(toAcc.type, toAcc);

        this.flashMessageService.show('Your transfer was done successfully', {cssClass: 'alert-success', timeout: 4000});
      } else if (this.typeTransfer === 'toOthers') {

        // Get Receiving Account Info
        this.getAccount(typeTo, toId, fromAcc, money, first, last);
      }
    }

    this.transferForm.reset();
  }

  getAccount(type, accId, fromAcc, money, first, last) {
    this.subscriptionAcc = this.accountService
    .getAccountById(type, accId)
    .subscribe(result => {

      this.toOtherAccount = result;
      this.toOtherAccount.type = type;

      // Get Receiving Client Info
      this.getClient(this.toOtherAccount.clientId, fromAcc, money, first, last);

    }, error => {
      console.log('Error getting account ', error);
      console.log('Receiving Account does not exist');

      this.flashMessageService.show('The receiving account does not exist. ' +
      'Please verify your information.', {cssClass: 'alert-danger', timeout: 4000});
    });
  }

  getClient(clientId, fromAcc, money, first, last) {
    this.subscriptionClient = this.clientService
    .getClientById(clientId)
    .subscribe(result => {

      this.client = result;

      // Verify Client Info to provided firstname & lastname
      if (this.client.firstname !== first || this.client.lastname !== last) {
        console.log('Client do not match with provided information');

        this.flashMessageService.show('Client do not match with provided information. ' +
        'Please verify your information.', {cssClass: 'alert-danger', timeout: 4000});

      } else {

        // Make Transfer
        fromAcc.amount = fromAcc.amount - money;
        this.toOtherAccount.amount = this.toOtherAccount.amount + money;

        // Update Accounts
        this.updateAccount(fromAcc.type, fromAcc);
        this.updateAccount(this.toOtherAccount.type, this.toOtherAccount);

        this.flashMessageService.show('Your transfer was done successfully', {cssClass: 'alert-success', timeout: 4000});
      }

    }, error => {
      console.log('Error getting client ', error);

      this.flashMessageService.show('Error doing the transfer. Please try again.', {cssClass: 'alert-danger', timeout: 4000});

    });
  }

  updateAccount(typeAcc, acc) {
    this.subscriptionUpdateAcc = this.accountService
    .updateAccount(typeAcc, acc.id, acc)
    .subscribe(result => {
      acc = result;
    }, error => {
      console.log('Error updating account ', error);

      this.flashMessageService.show('Error doing the transfer. Please try again.', {cssClass: 'alert-danger', timeout: 4000});
    });
  }


  get fromAccount() {
    return this.transferForm.get('fromAccount');
  }
  get toAccount() {
    return this.transferForm.get('toAccount');
  }
  get amount() {
    return this.transferForm.get('amount');
  }
  get typeToAccount() {
    return this.transferForm.get('typeToAccount');
  }
  get firstname() {
    return this.transferForm.get('firstname');
  }
  get lastname() {
    return this.transferForm.get('lastname');
  }

  ngOnDestroy() {
    if (this.subscriptionSpendingAccounts !== undefined) {
      this.subscriptionSpendingAccounts.unsubscribe();
    }
    if (this.subscriptionSavingAccounts !== undefined) {
      this.subscriptionSavingAccounts.unsubscribe();
    }
    if (this.subscriptionUpdateAcc !== undefined) {
      this.subscriptionUpdateAcc.unsubscribe();
    }
    if (this.subscriptionAcc !== undefined) {
      this.subscriptionAcc.unsubscribe();
    }
  }
}
