import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IssueCertitifcatesComponent } from './issue-certitifcates.component';

describe('IssueCertitifcatesComponent', () => {
  let component: IssueCertitifcatesComponent;
  let fixture: ComponentFixture<IssueCertitifcatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IssueCertitifcatesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IssueCertitifcatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
