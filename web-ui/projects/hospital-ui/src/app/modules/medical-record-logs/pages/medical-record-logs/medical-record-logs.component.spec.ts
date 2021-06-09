import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalRecordLogsComponent } from './medical-record-logs.component';

describe('MedicalRecordLogsComponent', () => {
  let component: MedicalRecordLogsComponent;
  let fixture: ComponentFixture<MedicalRecordLogsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedicalRecordLogsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MedicalRecordLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
