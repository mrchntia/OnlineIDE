import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-share-dialog',
  templateUrl: './share-dialog.component.html',
  styleUrls: ['./share-dialog.component.css']
})
export class ShareDialogComponent implements OnInit {
  public newUser: string;
  public projectName: string;

  // dialogRef: allows this component to close the dialog
  constructor(private dialogRef: MatDialogRef<ShareDialogComponent>,
              @Inject(MAT_DIALOG_DATA) data) {
    this.projectName = data.projectName;
  }

  ngOnInit(): void {}

  onCancel(): void {
    this.dialogRef.close();
  }

  onOk(): void {
    this.dialogRef.close(this.newUser);
  }
}
