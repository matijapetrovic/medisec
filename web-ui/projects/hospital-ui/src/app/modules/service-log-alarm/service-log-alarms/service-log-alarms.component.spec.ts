import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceLogAlarmsComponent } from './service-log-alarms.component';

describe('ServiceLogAlarmsComponent', () => {
  let component: ServiceLogAlarmsComponent;
  let fixture: ComponentFixture<ServiceLogAlarmsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceLogAlarmsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceLogAlarmsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
