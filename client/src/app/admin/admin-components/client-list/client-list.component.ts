import { Component, OnInit, OnDestroy } from '@angular/core';
import { ClientService } from 'src/app/service/client.service';
import { Client } from 'src/app/model/client';
import { Subscription } from 'rxjs';
import { AccountService } from 'src/app/service/account.service';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit, OnDestroy {

  private clients: Client[];
  private client: Client;
  private subscriptionClient: Subscription;
  private subscriptionDeleteClient: Subscription;
  private subscriptionDeleteSavingAccount: Subscription;
  private subscriptionDeleteSpendingAccount: Subscription;
  private subscriptionFindClient: Subscription;


  constructor(private clientService: ClientService, private accountService: AccountService) { }

  ngOnInit() {
    this.getAllClients();
  }

  getAllClients() {
    this.subscriptionClient = this.clientService
    .getAllClients()
    .subscribe(result => {
      this.clients = result;
    }, error => {
      console.log('Error getAllClients ', error);
    });
  }

  deleteClientConfirm(clientId) {

    if (confirm('Are you sure to want to delete this client and its accounts?')) {

      let client: Client;

      this.subscriptionFindClient = this.clientService
      .getClientById(clientId)
      .subscribe(result => {

        client = result;

        if (client.accounts !== null) {

          if (client.accounts.length >= 1) {
            this.deleteSavingAccounts(client);
          }

          if (client.accounts.length === 1) {
            this.deleteSpendingAccounts(client);
          }
        } else {
          this.deleteClient(clientId);
        }
      }, error => {
        console.log('Client not found', error);
      });
    }
}

  deleteSavingAccounts(client) {

    for (let i = 1; i < client.accounts.length; i++) {

      const accountId = +client.accounts[i].id;

      this.subscriptionDeleteSavingAccount = this.accountService
      .removeAccount('saving', accountId)
      .subscribe(result => {
        console.log('Deleted saving account successfuly', result);

        if (i === client.accounts.length - 1) {
          this.deleteSpendingAccounts(client);
        }

      }, error => {
        console.log('Error deleting saving account', error);
      });
    }
  }

  deleteSpendingAccounts(client) {

    const accountId = +client.accounts[0].id;

    this.subscriptionDeleteSpendingAccount = this.accountService
    .removeAccount('spending', accountId)
    .subscribe(result => {
      console.log('Deleted spending account successfuly', result);
      this.deleteClient(client.id);

    }, error => {
      console.log('Error deleting spending account', error);
    });
  }

  deleteClient(clientId) {

    this.subscriptionDeleteClient = this.clientService
    .removeClient(clientId)
    .subscribe(result => {
      console.log('Deleted client successfully ', result);
    this.getAllClients();
    }, error => {
        console.log('Error deleting client ', error);
    });
  }

  ngOnDestroy() {
    if (this.subscriptionClient !== undefined) {
      this.subscriptionClient.unsubscribe();
    }
    if (this.subscriptionDeleteClient !== undefined) {
      this.subscriptionDeleteClient.unsubscribe();
    }

    if (this.subscriptionDeleteSavingAccount !== undefined) {
      this.subscriptionDeleteSavingAccount.unsubscribe();
    }

    if (this.subscriptionDeleteSpendingAccount !== undefined) {
      this.subscriptionDeleteSpendingAccount.unsubscribe();
    }

    if (this.subscriptionFindClient !== undefined) {
      this.subscriptionFindClient.unsubscribe();
    }
  }

}
