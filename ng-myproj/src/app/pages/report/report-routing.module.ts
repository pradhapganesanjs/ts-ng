import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ReportContainerComponent } from './report-container/report-container.component';
import { ReportGridComponent } from './report-grid/report-grid.component';
import { ReportPanelComponent } from './report-panel/report-panel.component';

const routes: Routes = [
                { path: '', redirectTo: 'report', pathMatch: 'full'  },
                { path: 'report', component : ReportGridComponent},
                { path: 'panel', component : ReportContainerComponent}
            ];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReportRoutingModule { }
