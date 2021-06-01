import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevocationReasonSelectComponent } from './revocation-reason-select.component';

describe('RevocationReasonSelectComponent', () => {
  let component: RevocationReasonSelectComponent;
  let fixture: ComponentFixture<RevocationReasonSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RevocationReasonSelectComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RevocationReasonSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
