import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UploadDataComponent } from './upload-data/upload-data.component';
import { UploadConfigComponent } from './upload-config/upload-config.component';

const routes: Routes = [
  { path: '', redirectTo: 'upload', pathMatch: 'full'  },
  { path: 'upload', component : UploadDataComponent},
  { path: 'config', component : UploadConfigComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UploadRoutingModule { }
