import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentsbyMarketingComponent } from './documentsby-marketing.component';

describe('DocumentsbyMarketingComponent', () => {
  let component: DocumentsbyMarketingComponent;
  let fixture: ComponentFixture<DocumentsbyMarketingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DocumentsbyMarketingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentsbyMarketingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
