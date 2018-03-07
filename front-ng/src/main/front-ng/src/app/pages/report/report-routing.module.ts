import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ReportAllComponent } from './report-all/report-all.component';
import { ReportComponent } from './report.component';
import { ReportFetchComponent } from './report-fetch/report-fetch.component';
import { ReportLimitComponent } from './report-limit/report-limit.component';
import { SentlogComponent } from './sentlog/sentlog.component';

const routes: Routes = [
                { path: '', redirectTo: 'report', pathMatch: 'full'  },
                { path: 'report' , component : ReportComponent },
                { path: 'all' , component : ReportAllComponent },
                { path: 'fetch', component : ReportFetchComponent },
                { path: 'limit', component : ReportLimitComponent },
                { path: 'sentlog', component : SentlogComponent}
              ];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReportRoutingModule { }
