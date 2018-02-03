import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatStepperModule,
} from '@angular/material';
import {CdkTableModule} from '@angular/cdk/table';
//import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { MenubarMatComponent } from './menubar-mat/menubar-mat.component';
import { SelectMatComponent } from './select-mat/select-mat.component';
import { ButtonMatComponent } from './button-mat/button-mat.component';
import { InputMatComponent } from './input-mat/input-mat.component';
import { PanelMatComponent } from './panel-mat/panel-mat.component';
import { ContentMatComponent } from './content-mat/content-mat.component';
import { ToolbarMatComponent } from './toolbar-mat/toolbar-mat.component';
import { MenuMatComponent } from './menu-mat/menu-mat.component';


@NgModule({
  declarations: [SelectMatComponent
                  , ButtonMatComponent
                  , InputMatComponent
                  , PanelMatComponent
                  , ContentMatComponent
                  , ToolbarMatComponent
                  , MenuMatComponent
                  , MenubarMatComponent]
  , imports: [
                CommonModule,
                MatToolbarModule,
                MatSortModule,
                MatTableModule,
                MatSelectModule,
                MatPaginatorModule,
                MatMenuModule,
                MatInputModule,
                MatButtonModule,
                MatCardModule
             ]
  , exports: [
                CommonModule
                , SelectMatComponent
                , ButtonMatComponent
                , InputMatComponent
                , PanelMatComponent
                , ToolbarMatComponent, MenuMatComponent, ContentMatComponent]
})
export class SharedModule { }
