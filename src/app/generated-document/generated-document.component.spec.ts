import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GeneratedDocumentComponent } from './generated-document.component';

describe('GeneratedDocumentComponent', () => {
  let component: GeneratedDocumentComponent;
  let fixture: ComponentFixture<GeneratedDocumentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GeneratedDocumentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GeneratedDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
