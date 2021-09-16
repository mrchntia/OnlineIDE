import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-rename-dialog',
  templateUrl: './rename-dialog.component.html',
  styleUrls: ['./rename-dialog.component.css']
})
export class RenameDialogComponent implements OnInit {
  public oldName: string;
  public newName: string;

  // dialogRef: allows this component to close the dialog
  constructor(private dialogRef: MatDialogRef<RenameDialogComponent>,
              @Inject(MAT_DIALOG_DATA) data) {

    this.oldName = data.oldName;
  }

  ngOnInit(): void {}

  onCancel(): void {
    this.dialogRef.close();
  }

  onOk(): void {
    this.dialogRef.close(this.newName);
  }
}
