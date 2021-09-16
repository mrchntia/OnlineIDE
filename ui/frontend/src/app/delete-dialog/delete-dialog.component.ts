import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-delete-dialog',
  templateUrl: './delete-dialog.component.html',
  styleUrls: ['./delete-dialog.component.css']
})
export class DeleteDialogComponent implements OnInit {
  public typeName: string; // item type (Project / File)
  public itemToDeleteName: string;

  // dialogRef: allows this component to close the dialog
  constructor(
    private dialogRef: MatDialogRef<DeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data) {

    this.typeName = data.typeName;
    this.itemToDeleteName = data.itemToDeleteName;
  }

  ngOnInit(): void {
  }

  onCancel(): void {
    this.dialogRef.close(false); // item should not be deleted
  }

  onOk(): void {
    this.dialogRef.close(true); // item should be deleted
  }
}
