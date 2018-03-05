import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from '@app/home';
import { LoginComponent } from '@app/pages/login';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginDashboardComponent } from '@app/pages/login';
// import { ReportModule } from '@app/pages/report';

const appRoutes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full'}
    , { path: 'home', component: HomeComponent }
    , { path: 'login', component: LoginComponent }
    , { path: 'logindashboard', component: LoginDashboardComponent }
    , { path: 'report', loadChildren: './pages/report/report.module#ReportModule'}
    , { path: 'upload', loadChildren: './pages/upload/upload.module#UploadModule'}
    , { path: 'logout', loadChildren: './pages/logout/logout.module#LogoutModule'}
    , { path: 'profile', loadChildren: './pages/profile/profile.module#ProfileModule'}
];
    // , { path: 'report/**', loadChildren: './pages/report/report.module#ReportModule'}
    // , { path: '**', redirectTo: ''  } // default redirection to home

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})

export class AppRouteModule { }
