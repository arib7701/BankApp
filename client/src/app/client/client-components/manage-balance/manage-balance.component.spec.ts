import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageBalanceComponent } from './manage-balance.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TestsModule } from 'src/app/tests/tests.module';

describe('ManageBalanceComponent', () => {
  let component: ManageBalanceComponent;
  let fixture: ComponentFixture<ManageBalanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
        ManageBalanceComponent ],
      schemas: [
         NO_ERRORS_SCHEMA ],
      imports: [
        TestsModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageBalanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
