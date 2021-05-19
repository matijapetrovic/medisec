import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IssueCertificateFormComponent } from './issue-certificate-form.component';

describe('IssueCertificateFormComponent', () => {
  let component: IssueCertificateFormComponent;
  let fixture: ComponentFixture<IssueCertificateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IssueCertificateFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IssueCertificateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
