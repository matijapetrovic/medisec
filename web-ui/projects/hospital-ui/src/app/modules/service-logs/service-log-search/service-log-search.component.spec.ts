import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceLogSearchComponent } from './service-log-search.component';

describe('ServiceLogSearchComponent', () => {
  let component: ServiceLogSearchComponent;
  let fixture: ComponentFixture<ServiceLogSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceLogSearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceLogSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
