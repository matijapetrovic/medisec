import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogSourceFormComponent } from './log-source-form.component';

describe('LogSourceFormComponent', () => {
  let component: LogSourceFormComponent;
  let fixture: ComponentFixture<LogSourceFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogSourceFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LogSourceFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
