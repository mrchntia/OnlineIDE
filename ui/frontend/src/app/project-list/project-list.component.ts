import {Component, OnInit} from '@angular/core';
import {Project} from "../project";
import {HttpService} from "../service/http.service";
import {NewDialogComponent} from "../new-dialog/new-dialog.component";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {DeleteDialogComponent} from "../delete-dialog/delete-dialog.component";
import {Router} from "@angular/router";
import {RenameDialogComponent} from "../rename-dialog/rename-dialog.component";

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {
  public projects: Project[];

  constructor(private httpService: HttpService, private dialog: MatDialog,
              private router: Router) {

    this.httpService.getAllProjects().subscribe(
      response => {
        this.projects = response;
      }
    );
  }

  ngOnInit(): void {}

  public createNewProjectViaDialog(): void {
    let dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true; // disable closing by clicking next to dialog
    dialogConfig.autoFocus = true;
    dialogConfig.data = { // data is passed to the dialog component with dialog.open below
      typeName: 'Project'
    };

    const dialogRef = this.dialog.open(NewDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      // response is empty if dialog was canceled
      response => {
        if (response && response.length > 0) {
          this.createProject(response);
        }
      }
    );
  }

  public deleteProjectViaDialog(projectToDelete: Project): void {
    let dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      typeName: 'Project',
      itemToDeleteName: projectToDelete.name
    };

    const dialogRef = this.dialog.open(DeleteDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      // response === true if project should be deleted
      (response: boolean) => {
        if (response) {
          this.deleteProject(projectToDelete.id);
        }
      }
    );
  }

  public renameProjectViaDialog(projectToRename: Project): void {
    let dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      oldName: projectToRename.name
    };

    const dialogRef = this.dialog.open(RenameDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      // response is empty if dialog was canceled
      (response: string) => {
        if (response && response.length > 0) {
          this.renameProject(projectToRename, response);
        }
      }
    );
  }

  public navigateToSourceFileList(project: Project): void {
    // state can be accessed by the SourceFileListComponent
    this.router.navigateByUrl('/ui/files', {state: { project: project }});
  }

  private createProject(newProjectName: string): void {
    this.httpService.createProject(newProjectName).subscribe(
      response => {
        this.projects.push(response);
      }
    );
  }

  private deleteProject(deleteProjectId: string): void {
    this.httpService.deleteProject(deleteProjectId).subscribe();
    let indexToRemove = this.projects.indexOf(this.projects.find(p => p.id === deleteProjectId));
    this.projects.splice(indexToRemove, 1);
  }

  private renameProject(projectToRename: Project, newProjectName: string): void {
    this.httpService.updateProjectName(projectToRename.id, newProjectName).subscribe(
      response => {
        if (response.name && response.id) {
          let indexToRename = this.projects.indexOf(this.projects.find(p => p.id === projectToRename.id));
          this.projects[indexToRename].name = response.name;
          this.projects[indexToRename].id = response.id;
        }
      }
    );
  }
}
