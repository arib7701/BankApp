import { Component, OnInit, OnDestroy } from '@angular/core';
import { Account } from 'src/app/model/account';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from '../../../service/account.service';
import { FlashMessagesService } from 'angular2-flash-messages';

@Component({
  selector: 'app-account-edit-details',
  templateUrl: './account-edit-details.component.html',
  styleUrls: ['./account-edit-details.component.css']
})
export class AccountEditDetailsComponent implements OnInit, OnDestroy {
  type: String;
  accountId: number;
  clientNumber: number;
  editAccountForm: FormGroup;
  account: Account = new Account();
  subscriptionEditAccount: Subscription;
  subscriptionAccount: Subscription;

  constructor(
    private route: ActivatedRoute,
    private accountService: AccountService,
    private flashMessageService: FlashMessagesService) { }

  ngOnInit() {
      this.type = this.route.snapshot.paramMap.get('type');
      this.accountId = +this.route.snapshot.paramMap.get('id');
      this.createForm(this.type);

      this.subscriptionAccount = this.accountService
      .getAccountById(this.type, this.accountId)
      .subscribe(result => {
        this.account = result;

        // Set value of Form
        this.editAccountForm.controls['name'].setValue(this.account.name);
        this.editAccountForm.controls['amount'].setValue(this.account.amount);
        this.editAccountForm.controls['clientId'].setValue(this.account.clientId);

        if (this.type === 'spending') {
          this.editAccountForm.controls['credit'].setValue(this.account.credit);
        } else if (this.type === 'saving') {
          this.editAccountForm.controls['minimum'].setValue(this.account.minimum);
          this.editAccountForm.controls['interest'].setValue(this.account.interest);
        }

        this.clientNumber = this.account.clientId;

      }, error => {
        console.log('Error get account ', error);
      });

  }

  createForm(type: String) {

    if (type === 'spending') {
      this.editAccountForm = new FormGroup({
        name: new FormControl({value: ''}, [Validators.required]),
        amount: new FormControl({value: '', disabled: true}, [Validators.required]),
        clientId: new FormControl({value: '', disabled: true}, [Validators.required]),
        credit: new FormControl({value: ''}, [Validators.required, Validators.max(9999)])
      });

    } else if (type === 'saving') {

      this.editAccountForm = new FormGroup({
        name: new FormControl({value: ''}, [Validators.required]),
        amount: new FormControl({value: '', disabled: true}, [Validators.required]),
        clientId: new FormControl({value: '', disabled: true}, [Validators.required]),
        minimum: new FormControl('', [Validators.required, Validators.max(99)]),
        interest: new FormControl('', [Validators.required, Validators.max(99)])
      });
    }
  }

  onSubmit(type: String) {

    if (type === 'spending') {
      this.account.name = this.editAccountForm.value.name;
      this.account.type = 'spending';
      this.account.credit = this.editAccountForm.value.credit;
    } else if (type === 'saving') {
      this.account.name = this.editAccountForm.value.name;
      this.account.type = 'saving';
      this.account.minimum = this.editAccountForm.value.minimum;
      this.account.interest = this.editAccountForm.value.interest;
    }

    if (this.account.credit > 1000) {
      this.flashMessageService.show('The Credit provided is higher than the maximum' +
      'credit autorized ($1000). Please try again with a lesser amount.', {cssClass: 'alert-danger', timeout: 4000});
    } else {

    }

    console.log('account ', this.account);

    this.subscriptionEditAccount = this.accountService
    .updateAccount(type, this.accountId, this.account)
    .subscribe(result => {

      this.flashMessageService.show('Account edited successfully', {cssClass: 'alert-success', timeout: 4000});
      console.log('Account edited successfully ', result);

    }, error => {
      console.log('Error edited account ', error);
      this.flashMessageService.show('Error updating account. Please try again later', {cssClass: 'alert-danger', timeout: 4000});
    });
  }

  get name() {
    return this.editAccountForm.get('name');
  }
  get amount() {
    return this.editAccountForm.get('amount');
  }
  get clientId() {
    return this.editAccountForm.get('clientId');
  }
  get credit() {
    return this.editAccountForm.get('credit');
  }
  get minimum() {
    return this.editAccountForm.get('minimum');
  }
  get interest() {
    return this.editAccountForm.get('interest');
  }

  ngOnDestroy() {
    if (this.subscriptionEditAccount !== undefined) {
      this.subscriptionEditAccount.unsubscribe();
    }
    if (this.subscriptionAccount !== undefined) {
      this.subscriptionAccount.unsubscribe();
    }
  }

}
