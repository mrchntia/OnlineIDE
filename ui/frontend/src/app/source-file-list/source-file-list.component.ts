import {Component, Input, OnInit} from '@angular/core';
import {HttpService} from "../service/http.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {SourceFile} from "../sourceFile";
import {Project} from "../project";
import {NewDialogComponent} from "../new-dialog/new-dialog.component";
import {DeleteDialogComponent} from "../delete-dialog/delete-dialog.component";
import {Router} from "@angular/router";
import {RenameDialogComponent} from "../rename-dialog/rename-dialog.component";
import {ShareDialogComponent} from "../share-dialog/share-dialog.component";

@Component({
  selector: 'app-source-file-list',
  templateUrl: './source-file-list.component.html',
  styleUrls: ['./source-file-list.component.css']
})
export class SourceFileListComponent implements OnInit {

  public project: Project;
  public sourceFiles: SourceFile[];

  constructor(private httpService: HttpService, private dialog: MatDialog,
              private router: Router) {

    // Initialize just to be safe
    this.project = new Project();
    this.project.name = "";
    this.project.id = "";
    this.project.users = [];
    this.sourceFiles = [];

    // passed by ProjectListComponent via router.navigateByUrl,
    // undefined if page is accessed directly / reloaded
    let passedState = this.router.getCurrentNavigation().extras.state;
    if (!passedState) {
      router.navigateByUrl('ui/projects');
    }
    else {
      this.project = passedState.project;
      this.httpService.getAllSourceFilesOfProject(this.project.id).subscribe(
        response => {
          this.sourceFiles = response;
        }
      );
    }
  }

  ngOnInit(): void {}

  public createNewSourceFileViaDialog(): void {
    let newSourceFileName = '';
    let dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      typeName: 'File',
    };

    const dialogRef = this.dialog.open(NewDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      // response is empty if dialog was canceled
      response => {
        newSourceFileName = response;
        if (newSourceFileName && newSourceFileName.length > 0) {
          this.createSourceFile(newSourceFileName);
        }
      }
    );
  }

  public deleteSourceFileViaDialog(sourceFileToDelete: SourceFile): void {
    let dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      typeName: 'File',
      itemToDeleteName: sourceFileToDelete.name
    };

    const dialogRef = this.dialog.open(DeleteDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      // response === true if file should be deleted
      (response: boolean) => {
        if (response) {
          this.deleteSourceFile(sourceFileToDelete.id);
        }
      }
    );
  }

  public renameSourceFileViaDialog(sourceFileToRename: SourceFile): void {
    let dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      oldName: sourceFileToRename.name
    };

    const dialogRef = this.dialog.open(RenameDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      // response is empty if dialog was canceled
      (response: string) => {
        if (response && response.length > 0) {
          this.renameSourceFile(sourceFileToRename, response);
        }
      }
    );
  }

  public shareProjectViaDialog(projectName: string): void {
    let dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
        projectName: projectName
    };
    const dialogRef = this.dialog.open(ShareDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(
      // response is empty if dialog was canceled
      (response: string) => {
        if (response && response.length > 0) {
          this.shareProject(this.project.id,
            response);
        }
      }
    );
  }

  public navigateToEditor(sourceFile: SourceFile): void {
    // state can be accessed by the editor
    this.router.navigateByUrl('/ui/editor', {state: { sourceFile: sourceFile }});
  }

  private createSourceFile(newSourceFileName: string): void {
    this.httpService.createSourceFile(newSourceFileName, this.project).subscribe(
      response => {
        this.sourceFiles.push(response);
      }
    );
  }

  private deleteSourceFile(deleteSourceFileId): void {
    this.httpService.deleteSourceFile(deleteSourceFileId).subscribe();
    let indexToRemove = this.sourceFiles.indexOf(this.sourceFiles.find(f => f.id === deleteSourceFileId));
    this.sourceFiles.splice(indexToRemove, 1);
  }

  private renameSourceFile(sourceFileToRename: SourceFile, newSourceFileName: string): void {
    this.httpService.updateSourceFileName(sourceFileToRename.id, newSourceFileName).subscribe(
      response => {
        if (response.name && response.id) {
          let indexToRename = this.sourceFiles.indexOf(this.sourceFiles.find(f => f.id === sourceFileToRename.id));
          this.sourceFiles[indexToRename].name = response.name;
          this.sourceFiles[indexToRename].id = response.id;
        }
      }
    );
  }

  //sourceFileId also as parameter?
  private shareProject(ProjectId: string, User: string) {
    this.httpService.shareProject(ProjectId, User).subscribe(
      response => {
        //
      }
    );

  }
}
