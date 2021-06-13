import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogSourcesListComponent } from './log-sources-list.component';

describe('LogSourcesListComponent', () => {
  let component: LogSourcesListComponent;
  let fixture: ComponentFixture<LogSourcesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogSourcesListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LogSourcesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
