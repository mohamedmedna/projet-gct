import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkerDashComponent } from './worker-dash.component';

describe('WorkerDashComponent', () => {
  let component: WorkerDashComponent;
  let fixture: ComponentFixture<WorkerDashComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WorkerDashComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkerDashComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
