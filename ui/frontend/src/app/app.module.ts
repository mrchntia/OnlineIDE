import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule} from "@angular/forms";
import {Route, RouterModule} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";

import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatDividerModule} from "@angular/material/divider";
import {MatLineModule} from "@angular/material/core";
import {MatDialogModule} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatCardModule} from "@angular/material/card";
import {MatToolbarModule} from "@angular/material/toolbar";

import {AppComponent} from './app.component';
import {ProjectListComponent} from './project-list/project-list.component';
import {NewDialogComponent} from './new-dialog/new-dialog.component';
import {MonacoEditorModule} from "ngx-monaco-editor";
import {EditorComponent} from './editor/editor.component';
import {SourceFileListComponent} from './source-file-list/source-file-list.component';
import {DeleteDialogComponent} from './delete-dialog/delete-dialog.component';
import {RenameDialogComponent} from './rename-dialog/rename-dialog.component';
import { ShareDialogComponent } from './share-dialog/share-dialog.component';
import {AuthGuard} from "./auth.guard";
import {LoginComponent} from './login/login.component';

const routes: Route[] = [
  {path: 'ui/projects', component: ProjectListComponent, canActivate: [AuthGuard]},
  {path: 'ui/files', component: SourceFileListComponent},
  {path: 'ui/editor', component: EditorComponent, canActivate: [AuthGuard]},
  {path: 'ui/login', component: LoginComponent},
  {path: '**', redirectTo: '/ui/login'}
]

@NgModule({
  declarations: [
    AppComponent,
    ProjectListComponent,
    EditorComponent,
    NewDialogComponent,
    SourceFileListComponent,
    DeleteDialogComponent,
    RenameDialogComponent,
    ShareDialogComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    RouterModule.forRoot(routes),
    MonacoEditorModule.forRoot(),
    HttpClientModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
    MatDividerModule,
    MatLineModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatToolbarModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
