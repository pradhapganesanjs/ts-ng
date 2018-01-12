import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AdminComponent } from './core/admin/admin.component';
import { UploadComponent } from './pages/upload/upload.component';
import { ReportComponent } from './core/report/report.component';
import { ReportGridComponent } from './pages/report-grid/report-grid.component';
//import { ProfileComponent } from './pages/profile/profile.component';
//import { AccountsComponent } from './pages/accounts/accounts.component';

const appRoutes: Routes = [
    { path: '', component: HomeComponent}
    , { path: 'home', component: HomeComponent}
    , { path: 'dashboard', component : DashboardComponent,
        children : [
         { path: 'admin', component : AdminComponent,
            children : [
                { path: 'upload', component : UploadComponent}]
         },
         { path: 'report', component : ReportComponent,
            children : [
                 { path: 'grid', component : ReportGridComponent}]
         }

         //{ path: 'upload', component : AdminComponent},
    //     { path: 'accounts', component : AccountsComponent }
    //
     ]}
    , { path: '**', redirectTo: '' } // default redirection to home
];

export const RouteConfig = RouterModule.forRoot(appRoutes);
