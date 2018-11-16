import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientComponent } from './client.component';
import { clientRoutes } from './client.routes';
import { HomeComponent } from './client-components/home/home.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { NavbarComponent } from './client-components/navbar/navbar.component';
import { FooterComponent } from './client-components/footer/footer.component';
import { TransfertComponent } from './client-components/transfert/transfert.component';
import { AccountDetailsComponent } from './client-components/account-details/account-details.component';
import { AccountListComponent } from './client-components/account-list/account-list.component';
import { ProfilPageComponent } from './client-components/profil-page/profil-page.component';
import { ProfilPageEditComponent } from './client-components/profil-page-edit/profil-page-edit.component';
import { LoginComponent } from './client-components/login/login.component';
import { ManageBalanceComponent } from './client-components/manage-balance/manage-balance.component';
import { ManageTransferComponent } from './client-components/manage-transfer/manage-transfer.component';


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild (
      clientRoutes
    ),
    SharedModule
  ],
  declarations: [
     ClientComponent,
     LoginComponent,
     HomeComponent,
     NavbarComponent,
     FooterComponent,    
     TransfertComponent, 
     AccountDetailsComponent, 
     AccountListComponent, 
     ProfilPageComponent, 
     ProfilPageEditComponent, ManageBalanceComponent, ManageTransferComponent
    ]
})
export class ClientModule { }
