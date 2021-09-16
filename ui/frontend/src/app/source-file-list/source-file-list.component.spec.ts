import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SourceFileListComponent } from './source-file-list.component';

describe('SourceFileListComponent', () => {
  let component: SourceFileListComponent;
  let fixture: ComponentFixture<SourceFileListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SourceFileListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SourceFileListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
