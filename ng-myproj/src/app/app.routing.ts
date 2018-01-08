import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { AccountsComponent } from './pages/accounts/accounts.component';

const appRoutes: Routes = [
    { path: '**', redirectTo: '' }, // default redirection to home
    { path: '', component: HomeComponent},
    { path: 'home', component: HomeComponent}
    //,{ path: 'dashboard', component : DashboardComponent, children : [
    //     { path: 'profile', component : ProfileComponent },
    //     { path: 'accounts', component : AccountsComponent }
    // ]}
];

export const RouteConfig = RouterModule.forRoot(appRoutes);
