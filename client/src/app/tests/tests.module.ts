import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FlashMessagesModule } from 'angular2-flash-messages';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterTestingModule,
    HttpClientTestingModule,
    FlashMessagesModule.forRoot(),
    BrowserAnimationsModule
  ],
  schemas: [
    NO_ERRORS_SCHEMA
  ],
  exports: [
    FormsModule,
    ReactiveFormsModule,
    RouterTestingModule,
    HttpClientTestingModule,
    FlashMessagesModule,
  ],

  declarations: []
})
export class TestsModule { }
