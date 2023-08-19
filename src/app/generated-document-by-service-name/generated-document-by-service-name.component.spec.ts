import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GeneratedDocumentByServiceNameComponent } from './generated-document-by-service-name.component';

describe('GeneratedDocumentByServiceNameComponent', () => {
  let component: GeneratedDocumentByServiceNameComponent;
  let fixture: ComponentFixture<GeneratedDocumentByServiceNameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GeneratedDocumentByServiceNameComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GeneratedDocumentByServiceNameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
