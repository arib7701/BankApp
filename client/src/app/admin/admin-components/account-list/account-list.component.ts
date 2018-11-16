import { Component, OnInit, OnDestroy } from '@angular/core';
import { AccountService } from '../../../service/account.service';
import { Subscription } from 'rxjs';
import { ClientService } from '../../../service/client.service';
import { Client } from '../../../model/client';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit, OnDestroy {
  allSpendingAccounts: any[];
  allSavingAccounts: any[];
  subscriptionSpendingAcc: Subscription;
  subscriptionSavingAcc: Subscription;
  subscriptionDeleteAccount: Subscription;
  subscriptionClient: Subscription;

  constructor(private accountService: AccountService, private clientService: ClientService) { }

  ngOnInit() {
    this.getAllSpendingAccounts();
    this.getAllSavingAccounts();
  }

  getAllSpendingAccounts() {

    this.subscriptionSpendingAcc = this.accountService
    .getAllAccounts('spending')
    .subscribe(result => {
      this.allSpendingAccounts = result;
      if (this.allSpendingAccounts !== null) {
        for (let i = 0; i < this.allSpendingAccounts.length; i++) {
          this.allSpendingAccounts[0].type = 'spending';
        }
      }

    }, error => {
      console.log('Error getting all spending accounts ', error);
    });
  }

  getAllSavingAccounts() {

    this.subscriptionSavingAcc = this.accountService
    .getAllAccounts('saving')
    .subscribe(result => {
      this.allSavingAccounts = result;

      if (this.allSavingAccounts !== null) {
        for (let i = 0; i < this.allSavingAccounts.length; i++) {
          this.allSavingAccounts[0].type = 'saving';
        }
      }

    }, error => {
      console.log('Error getting all saving accounts ', error);
    });
  }

  closeAccountConfirm(type, accountId, clientId) {
    if (type === 'saving') {
      if (confirm('Are you sure to want to delete this saving account? Your remaining balance will be move to your spending account.')) {

        // TO DO
        // move amount saving to spending

        this.closeAccount(type, accountId);
      }
    } else if ( type === 'spending') {

      let client: Client;
      this.subscriptionClient = this.clientService
      .getClientById(clientId)
      .subscribe(result => {
        client = result;

        if (client.accounts.length > 1) {
          alert('Please close your saving(s) accounts first.');
        } else {
          if (confirm('Are you sure to want to delete this spending account? ' +
          'Your remaining balance will be sent to you by mail as a check.')) {

            // TO DO
            // send check to client
            this.closeAccount(type, accountId);
          }
        }
      }, error => {
        console.log('Error getting client', error);
      });
    }
  }

  closeAccount(type, accountId) {
    this.subscriptionDeleteAccount = this.accountService
    .removeAccount(type, accountId)
    .subscribe(result => {
      console.log('Account deleted successfully ', result);
      location.reload();
    }, error => {
      console.log('Error deleting account ', error);
    });
  }

  ngOnDestroy() {
    if (this.subscriptionSpendingAcc !== undefined) {
      this.subscriptionSpendingAcc.unsubscribe();
    }
    if (this.subscriptionSavingAcc !== undefined) {
      this.subscriptionSavingAcc.unsubscribe();
    }
  }

}
