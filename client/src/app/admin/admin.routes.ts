import {Routes, RouterModule} from '@angular/router';
import { AdminComponent } from './admin.component';
import { LoginComponent } from './admin-components/login/login.component';
import { ClientListComponent } from './admin-components/client-list/client-list.component';
import { ClientDetailComponent } from './admin-components/client-detail/client-detail.component';
import { ClientEditDetailsComponent } from './admin-components/client-edit-details/client-edit-details.component';
import { ClientCreateComponent } from './admin-components/client-create/client-create.component';
import { AccountListComponent } from './admin-components/account-list/account-list.component';
import { AccountCreateComponent } from './admin-components/account-create/account-create.component';
import { AccountEditDetailsComponent } from './admin-components/account-edit-details/account-edit-details.component';


export const adminRoutes: Routes = [

    {
        path: '',
        component: AdminComponent,
        children: [
            {
                path: '',
                component: LoginComponent
            },
            {
                path: 'client/list',
                component: ClientListComponent
            },
            {
                path: 'client/create',
                component: ClientCreateComponent
            },
            {
                path: 'client/:id',
                component: ClientDetailComponent
            },
            {
                path: 'client/edit/:id',
                component: ClientEditDetailsComponent
            },
            {
                path: 'account/list',
                component: AccountListComponent
            },
            {
                path: 'account/create/:type/:id',
                component: AccountCreateComponent
            },
            {
                path: 'account/edit/:type/:id',
                component: AccountEditDetailsComponent
            }
        ]
    }
];

