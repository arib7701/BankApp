import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { ClientService } from '../../../service/client.service';
import { ActivatedRoute } from '@angular/router';
import { Client } from '../../../model/client';
import { Account } from '../../../model/account';
import { Subscription } from 'rxjs';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';
import { AccountService } from '../../../service/account.service';

@Component({
  selector: 'app-client-detail',
  templateUrl: './client-detail.component.html',
  styleUrls: ['./client-detail.component.css']
})
export class ClientDetailComponent implements OnInit, OnDestroy {
  id: number;
  client: Client;
  accounts: any[];
  subscriptionClient: Subscription;
  subscriptionDeleteAccount: Subscription;
  subscriptionDeleteAllSaving: Subscription;
  newAccType: String = 'saving';

  constructor(
    private clientService: ClientService,
    private accountService: AccountService,
    private route: ActivatedRoute,
    iconRegistry: MatIconRegistry,
    sanitizer: DomSanitizer) {
    iconRegistry.addSvgIcon(
      'delete',
      sanitizer.bypassSecurityTrustResourceUrl('assets/image/baseline-delete-24px.svg'));
    iconRegistry.addSvgIcon(
      'edit',
      sanitizer.bypassSecurityTrustResourceUrl('assets/image/baseline-create-24px.svg'));
   }

  ngOnInit() {
    this.id = +this.route.snapshot.paramMap.get('id');
    this.getClientDetails();
  }

  getClientDetails() {
    this.subscriptionClient = this.clientService
    .getClientById(this.id)
    .subscribe(result => {
      this.client = result;

      if (this.client.accounts !== null) {

        this.accounts = this.client.accounts;
        this.newAccType = 'saving';

          this.accounts[0].type = 'spending';
          for (let i = 1; i < this.accounts.length; i ++) {
            this.accounts[i].type = 'saving';
          }
      } else {
        this.newAccType = 'spending';
      }
    }, error => {
      console.log('Error getClientDetail ', error);
    });
  }

  closeAccountConfirm(type, accountId) {
    if (type === 'saving') {
      if (confirm('Are you sure to want to delete this saving account? Your remaining balance will be move to your spending account.')) {

        // TO DO
        // move amount saving to spending

        this.closeAccount(type, accountId);
      }
    } else if ( type === 'spending') {

      if (this.client.accounts.length > 1) {

        alert('Please close your saving(s) accounts first.');

      } else {
        if (confirm('Are you sure to want to delete this spending account? ' +
        'Your remaining balance will be sent to you by mail as a check.')) {

          // TO DO
          // send check to client
          this.closeAccount(type, accountId);
        }
      }
    }
  }

  closeAllSavingConfirm() {
    if (confirm('Are you sure to want to delete all your saving accounts? ' +
    'Your remaining balances will be move to your spending account.')) {

      this.subscriptionDeleteAllSaving = this.accountService
      .removeAccountByClientId('saving', this.id)
      .subscribe(result => {
        console.log(result);
        this.getClientDetails();
      }, error => {
        console.log('Error removing all savings accounts ', error);
      });
    }
  }

  closeAccount(type, accountId) {
    this.subscriptionDeleteAccount = this.accountService
    .removeAccount(type, accountId)
    .subscribe(result => {
      console.log('Account deleted successfully ', result);
      this.getClientDetails();
    }, error => {
      console.log('Error deleting account ', error);
    });
  }

  ngOnDestroy() {
    if (this.subscriptionClient !== undefined) {
      this.subscriptionClient.unsubscribe();
    }
    if (this.subscriptionDeleteAccount !== undefined) {
      this.subscriptionDeleteAccount.unsubscribe();
    }
    if (this.subscriptionDeleteAllSaving !== undefined) {
      this.subscriptionDeleteAllSaving.unsubscribe();
    }
  }
}
