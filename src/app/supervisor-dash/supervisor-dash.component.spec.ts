import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SupervisorDashComponent } from './supervisor-dash.component';

describe('SupervisorDashComponent', () => {
  let component: SupervisorDashComponent;
  let fixture: ComponentFixture<SupervisorDashComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SupervisorDashComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SupervisorDashComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
