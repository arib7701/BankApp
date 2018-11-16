import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientEditDetailsComponent } from './client-edit-details.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TestsModule } from 'src/app/tests/tests.module';

describe('ClientEditDetailsComponent', () => {
  let component: ClientEditDetailsComponent;
  let fixture: ComponentFixture<ClientEditDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
        ClientEditDetailsComponent ],
      schemas: [ 
        NO_ERRORS_SCHEMA ],
      imports: [
          TestsModule
        ]  
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientEditDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
