import { TestBed } from '@angular/core/testing';

import { ClientService } from './client.service';
import { TestsModule } from '../tests/tests.module';

describe('ClientService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      ClientService
    ],
    imports: [
      TestsModule
    ]
  }));

  it('should be created', () => {
    const service: ClientService = TestBed.get(ClientService);
    expect(service).toBeTruthy();
  });
});
