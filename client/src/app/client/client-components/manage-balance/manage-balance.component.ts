import { Component, OnInit, Input, OnDestroy, EventEmitter, Output } from '@angular/core';
import { AccountService } from 'src/app/service/account.service';
import { Subscription, from } from 'rxjs';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Account } from '../../../model/account';
import { FlashMessagesService } from 'angular2-flash-messages';

@Component({
  selector: 'app-manage-balance',
  templateUrl: './manage-balance.component.html',
  styleUrls: ['./manage-balance.component.css']
})
export class ManageBalanceComponent implements OnInit, OnDestroy {

  @Input()
  typeChange: String;

  @Input()
  typeAccount: String;

  @Input()
  idAccount: number;

  @Output()
  reload: EventEmitter<String> = new EventEmitter();

  subscriptionAccount: Subscription;
  subscriptionUpdateAccount: Subscription;
  account: Account;
  changeBalanceForm: FormGroup;

  constructor(private accountService: AccountService,
    private flashMessagesService: FlashMessagesService) { }

  ngOnInit() {
    this.createForm();
    this.getAccountInfo();
  }

  createForm() {
    this.changeBalanceForm = new FormGroup({
      amount: new FormControl('', [Validators.required])
    });
  }

  getAccountInfo() {
    this.subscriptionAccount = this.accountService
    .getAccountById(this.typeAccount, this.idAccount)
    .subscribe(result => {
      this.account = result as Account;
    }, error => {
      console.log('Error getting account ', error);
    });
  }

  onSubmit() {

    const money = this.changeBalanceForm.value.amount;

    this.changeBalanceForm.reset();

    if (this.typeChange === 'credit') {
      this.account.amount = this.account.amount + money;
      this.updateAccount();

      this.flashMessagesService.show(
        'The credit was successfully applied to your account.',
        { cssClass: 'alert-success',
          timeout: 4000 });

    } else if (this.typeChange === 'debit') {

      if (money > this.account.amount + this.account.credit) {
        console.log('Not enough money on the account for this transaction');

        this.flashMessagesService.show(
          'Not enough money on the account for this transaction.',
          { cssClass: 'alert-danger',
            timeout: 4000 });

      } else {
        this.account.amount = this.account.amount - money;
        this.updateAccount();
      }
    }
  }

  updateAccount () {
    this.subscriptionUpdateAccount = this.accountService
    .updateAccount(this.typeAccount, this.idAccount, this.account)
    .subscribe(result => {
      this.account = result;
      this.reload.emit('reload');
      this.getAccountInfo();

      this.flashMessagesService.show(
        'The debit was successfully applied to your account.',
        { cssClass: 'alert-success',
          timeout: 4000 });

    }, error => {
      console.log('Error updating account ', error);

      this.flashMessagesService.show(
        'Error while making the transaction. Please try again later.',
        { cssClass: 'alert-danger',
          timeout: 4000 });
    });
  }

  get amount() {
    return this.changeBalanceForm.get('amount');
  }

  ngOnDestroy() {
    if (this.subscriptionAccount !== undefined) {
      this.subscriptionAccount.unsubscribe();
    }
    if (this.subscriptionUpdateAccount !== undefined) {
      this.subscriptionUpdateAccount.unsubscribe();
    }
  }

}
