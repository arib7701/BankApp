import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-transfert',
  templateUrl: './transfert.component.html',
  styleUrls: ['./transfert.component.css']
})
export class TransfertComponent implements OnInit {
  typeTransfer: String;
  betweenMyAccounts: Boolean = false;
  toOthers: Boolean = false;
  clientId: number;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.clientId = +this.route.snapshot.paramMap.get('id');
  }

  transfer(type) {

    if (type === 'myAccounts') {
      this.betweenMyAccounts = true;
      this.typeTransfer = 'myAccounts';

    } else if (type === 'toOthers') {
      this.toOthers = true;
      this.typeTransfer = 'toOthers';
    }
  }

}
