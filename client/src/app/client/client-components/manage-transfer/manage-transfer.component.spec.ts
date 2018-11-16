import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageTransferComponent } from './manage-transfer.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TestsModule } from 'src/app/tests/tests.module';
import { MatSelectModule, MatInputModule } from '@angular/material';

describe('ManageTransferComponent', () => {
  let component: ManageTransferComponent;
  let fixture: ComponentFixture<ManageTransferComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
        ManageTransferComponent ],
      schemas: [ 
        NO_ERRORS_SCHEMA ],
      imports: [
        TestsModule,
        MatSelectModule,
        MatInputModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageTransferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
