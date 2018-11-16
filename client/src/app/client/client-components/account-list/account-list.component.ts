import { Component, OnInit, OnDestroy } from '@angular/core';
import { AccountService } from '../../../service/account.service';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { Chart } from 'chart.js';
import { forEach } from '@angular/router/src/utils/collection';
import { MatIconRegistry } from '@angular/material';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit, OnDestroy {
  subscriptionSpendingAccount: Subscription;
  subscriptionSavingAccounts: Subscription;
  clientId: number;
  accounts: any[];

  pieChart = [];
  barChart = [];

  constructor(private route: ActivatedRoute,
    private accountService: AccountService,
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
    this.clientId = +this.route.snapshot.paramMap.get('id');
    this.getSpendingAccount();
  }

  getSpendingAccount() {
    this.subscriptionSpendingAccount = this.accountService
    .getAllAccountByClientId('spending', this.clientId)
    .subscribe(result => {

      const spending: any = result;

      if (spending.id !== 0) {
        this.accounts = new Array<any>();

        spending.type = 'spending';

        this.accounts.push(spending);
        this.getSavingAccounts();
      }
    }, error => {
      console.log('Error getting spending account ', error);
    });
  }

  getSavingAccounts() {
    this.subscriptionSavingAccounts = this.accountService
    .getAllAccountByClientId('saving', this.clientId)
    .subscribe(result => {

      for (const saving of result) {
        saving.type = 'saving';
        this.accounts.push(saving);
      }

      this.buildChart();
    }, error => {
      console.log('Error getting saving account ', error);
    });
  }

  buildChart() {

    const accountNames: String[] = new Array<String>();
    const accountAmount: number[] = new Array<number>();

    for (const acc of this.accounts) {
      accountNames.push(acc.name);
      accountAmount.push(acc.amount);
    }

    this.pieChart = new Chart('canvasPie', {
      type: 'doughnut',
      data: {
        labels: accountNames,
        datasets: [
          {
            data: accountAmount,
            backgroundColor: ['#3c8dbc', '#0073b7', '#00c0ef']
          }
        ]
      },
      options: {
        legend: {
          display: true
        },
        cutoutPercentage: 50
      }
    });

    this.barChart = new Chart('canvasBar', {
      type: 'bar',
      data: {
        labels: accountNames,
        datasets: [
          {
            data: accountAmount,
            backgroundColor: ['#3c8dbc', '#0073b7', '#00c0ef']
          }
        ]
      },
      options: {
        legend: {
          display: false
        },
        scales: {
          yAxes: [{
              ticks: {
                  suggestedMin: 0
              }
          }]
      }
      }
    });

  }

  ngOnDestroy() {
    if (this.subscriptionSpendingAccount !== undefined) {
      this.subscriptionSpendingAccount.unsubscribe();
    }
    if (this.subscriptionSavingAccounts !== undefined) {
      this.subscriptionSavingAccounts.unsubscribe();
    }
  }

}
