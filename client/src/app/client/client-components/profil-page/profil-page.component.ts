import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { ClientService } from '../../../service/client.service';
import { ActivatedRoute } from '@angular/router';
import { Client } from '../../../model/client';
import { Subscription } from 'rxjs';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';
import { AccountService } from '../../../service/account.service';

@Component({
  selector: 'app-client-detail',
  templateUrl: './profil-page.component.html',
  styleUrls: ['./profil-page.component.css']
})
export class ProfilPageComponent implements OnInit, OnDestroy {
  id: number;
  client: Client;
  accounts: any[];
  subscriptionClient: Subscription;
  subscriptionDeleteAccount: Subscription;
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

    this.subscriptionClient = this.clientService
    .getClientById(this.id)
    .subscribe(result => {
      this.client = result;
    }, error => {
      console.log('Error getClientDetail ', error);
    });
  }

  ngOnDestroy() {
    if (this.subscriptionClient !== undefined) {
      this.subscriptionClient.unsubscribe();
    }
  }
}
