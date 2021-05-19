import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificateRequestsComponent } from './certificate-requests.component';

describe('CertificateRequestsComponent', () => {
  let component: CertificateRequestsComponent;
  let fixture: ComponentFixture<CertificateRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CertificateRequestsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CertificateRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
