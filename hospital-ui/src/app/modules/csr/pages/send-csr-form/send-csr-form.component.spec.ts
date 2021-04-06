import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SendCsrFormComponent } from './send-csr-form.component';

describe('SendCsrFormComponent', () => {
  let component: SendCsrFormComponent;
  let fixture: ComponentFixture<SendCsrFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SendCsrFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SendCsrFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
