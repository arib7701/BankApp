import { TestBed } from '@angular/core/testing';

import { AccountService } from './account.service';
import { TestsModule } from '../tests/tests.module';

describe('AccountService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      AccountService
    ],
    imports: [
      TestsModule
    ]
  }));

  it('should be created', () => {
    const service: AccountService = TestBed.get(AccountService);
    expect(service).toBeTruthy();
  });
});
