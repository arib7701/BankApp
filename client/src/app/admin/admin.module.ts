import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminComponent } from './admin.component';
import { adminRoutes } from './admin.routes';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './admin-components/login/login.component';
import { ClientListComponent } from './admin-components/client-list/client-list.component';
import { SharedModule } from '../shared/shared.module';
import { ClientDetailComponent } from './admin-components/client-detail/client-detail.component';
import { NavbarComponent } from './admin-components/navbar/navbar.component';
import { FooterComponent } from './admin-components/footer/footer.component';
import { ClientEditDetailsComponent } from './admin-components/client-edit-details/client-edit-details.component';
import { ClientCreateComponent } from './admin-components/client-create/client-create.component';
import { AccountListComponent } from './admin-components/account-list/account-list.component';
import { AccountCreateComponent } from './admin-components/account-create/account-create.component';
import { AccountEditDetailsComponent } from './admin-components/account-edit-details/account-edit-details.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(
      adminRoutes
    ),
    SharedModule
  ],
  declarations: [
    AdminComponent,
    LoginComponent,
    ClientListComponent,
    ClientDetailComponent,
    NavbarComponent,
    FooterComponent,
    ClientEditDetailsComponent,
    ClientCreateComponent,
    AccountListComponent,
    AccountCreateComponent,
    AccountEditDetailsComponent
  ]
})
export class AdminModule { }
