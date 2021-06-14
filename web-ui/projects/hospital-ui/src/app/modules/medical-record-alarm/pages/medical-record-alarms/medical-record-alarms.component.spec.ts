import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalRecordAlarmsComponent } from './medical-record-alarms.component';

describe('MedicalRecordAlarmsComponent', () => {
  let component: MedicalRecordAlarmsComponent;
  let fixture: ComponentFixture<MedicalRecordAlarmsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedicalRecordAlarmsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MedicalRecordAlarmsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
