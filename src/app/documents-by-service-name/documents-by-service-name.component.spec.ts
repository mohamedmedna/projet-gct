import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentsByServiceNameComponent } from './documents-by-service-name.component';

describe('DocumentsByServiceNameComponent', () => {
  let component: DocumentsByServiceNameComponent;
  let fixture: ComponentFixture<DocumentsByServiceNameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DocumentsByServiceNameComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentsByServiceNameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
