import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { AccountService } from 'src/app/service/account.service';
import { Subscription } from 'rxjs';
import { Account } from './../../../model/account';
import { MatIconRegistry } from '@angular/material';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit, OnDestroy {
  id: number;
  type: string;
  account: Account;
  creditBalance: Boolean = false;
  debitBalance: Boolean = false;
  typeChange: String;
  subscriptionAccount: Subscription;
  subscriptionDeleteAccount: Subscription;

  constructor(private route: ActivatedRoute,
    private accountService: AccountService,
    private router: Router,
    iconRegistry: MatIconRegistry,
    sanitizer: DomSanitizer) {
    iconRegistry.addSvgIcon(
      'delete',
      sanitizer.bypassSecurityTrustResourceUrl('assets/image/baseline-delete-24px.svg'));
   }

  ngOnInit() {

    this.id = +this.route.snapshot.paramMap.get('id');
    this.type = this.route.snapshot.paramMap.get('type');
    this.getAccountInfo();
  }

  getAccountInfo() {
    this.subscriptionAccount = this.accountService
    .getAccountById(this.type, this.id)
    .subscribe(result => {
      this.account = result;
      this.account.type = this.type;
    }, error => {
      console.log('Error getting account ', error);
    });
  }

  changeBalance(type: string) {
    if (type === 'credit') {
      this.creditBalance = true;
      this.typeChange = 'credit';
    } else if (type === 'debit') {
      this.debitBalance = true;
      this.typeChange = 'debit';
    }
  }

  reload (event) {
    this.getAccountInfo();
  }

  closeAccountConfirm(accountId) {
    if (confirm('Are you sure to want to delete this saving account? Your remaining balance will be move to your spending account.')) {
      this.closeAccount(accountId);
    }
  }

  closeAccount(accountId) {
    this.subscriptionDeleteAccount = this.accountService
    .removeAccount('saving', accountId)
    .subscribe(result => {
      console.log('Account deleted successfully ', result);
      this.router.navigate(['/account/list/', this.account.clientId]);
    }, error => {
      console.log('Error deleting account ', error);
    });
  }

  ngOnDestroy() {
    if (this.subscriptionAccount !== undefined) {
      this.subscriptionAccount.unsubscribe();
    }
    if (this.subscriptionDeleteAccount !== undefined) {
      this.subscriptionDeleteAccount.unsubscribe();
    }
  }
}
