import {Routes, RouterModule} from '@angular/router';
import { ClientComponent } from './client.component';
import { AccountListComponent } from './client-components/account-list/account-list.component';
import { AccountDetailsComponent } from './client-components/account-details/account-details.component';
import { ProfilPageComponent } from './client-components/profil-page/profil-page.component';
import { ProfilPageEditComponent } from './client-components/profil-page-edit/profil-page-edit.component';
import { TransfertComponent } from './client-components/transfert/transfert.component';
import { HomeComponent } from './client-components/home/home.component';
import { LoginComponent } from './client-components/login/login.component';


export const clientRoutes: Routes = [

    {
        path: '',
        component: ClientComponent,
        children: [
            {
                path: '',
                component: HomeComponent
            },
            {
                path: 'login',
                component: LoginComponent
            },
            {
                path: 'account/list/:id',
                component: AccountListComponent
            },
            {
                path: 'account/details/:type/:id',
                component: AccountDetailsComponent
            },
            {
                path: 'profile/:id',
                component: ProfilPageComponent
            },
            {
                path: 'profile/edit/:id',
                component: ProfilPageEditComponent
            },
            {
                path: 'transfer/:id',
                component: TransfertComponent
            }

        ]
    }
]