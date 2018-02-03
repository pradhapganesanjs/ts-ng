import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from '@app/home';
import { LoginComponent } from '@app/pages/login';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginDashboardComponent } from '@app/pages/login';
import { ReportModule } from '@app/pages/report';

const appRoutes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full'}
    , { path: 'home', component: HomeComponent }
    , { path: 'login', component: LoginComponent }
    , { path: 'logindashboard', component: LoginDashboardComponent }
    , { path: 'report', loadChildren: './pages/report/report.module#ReportModule'}
];
    // , { path: '**', redirectTo: ''  } // default redirection to home

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})

export class AppRouteModule { }


// import { Routes, RouterModule } from '@angular/router';

// import { HomeComponent } from './home/home.component';
// import { DashboardComponent } from './dashboard/dashboard.component';
// import { AdminComponent } from './core/admin/admin.component';
// import { UploadComponent } from './pages/upload/upload.component';
// import { ReportComponent } from './core/report/report.component';
// import { ReportGridComponent } from './pages/report-grid/report-grid.component';
// import { ReportContainerComponent } from './pages/report-container/report-container.component';
// //import { ProfileComponent } from './pages/profile/profile.component';
// //import { AccountsComponent } from './pages/accounts/accounts.component';

// const appRoutes: Routes = [
//     { path: '', component: HomeComponent}
//     , { path: 'home', component: HomeComponent}
//     , { path: 'dashboard', component : DashboardComponent,
//         children : [
//          { path: 'admin', component : AdminComponent,
//             children : [
//                 { path: 'upload', component : UploadComponent}]
//          },
//          { path: 'report', component : ReportContainerComponent,
//             children : [
//                  { path: 'grid', component : ReportGridComponent}]
//          }

//          //{ path: 'upload', component : AdminComponent},
//     //     { path: 'accounts', component : AccountsComponent }
//     //
//      ]}
//     , { path: '**', redirectTo: '' } // default redirection to home
// ];

