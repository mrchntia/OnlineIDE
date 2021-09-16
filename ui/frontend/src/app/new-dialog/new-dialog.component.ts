import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-new-dialog',
  templateUrl: './new-dialog.component.html',
  styleUrls: ['./new-dialog.component.css']
})
export class NewDialogComponent implements OnInit {
  public typeName: string;
  public newItemName: string;

  // dialogRef: allows this component to close the dialog
  constructor(private dialogRef: MatDialogRef<NewDialogComponent>,
              @Inject(MAT_DIALOG_DATA) data) {

    this.typeName = data.typeName;
  }

  ngOnInit(): void {}

  onCancel(): void {
    this.dialogRef.close();
  }

  onOk(): void {
    this.dialogRef.close(this.newItemName);
  }
}
