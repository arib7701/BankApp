import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { AccountEditDetailsComponent } from './account-edit-details.component';
import { TestsModule } from 'src/app/tests/tests.module';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('AccountEditDetailsComponent', () => {
  let component: AccountEditDetailsComponent;
  let fixture: ComponentFixture<AccountEditDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
        AccountEditDetailsComponent ],
        schemas: [
           NO_ERRORS_SCHEMA ], 
        imports: [
          TestsModule
        ]    
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountEditDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
