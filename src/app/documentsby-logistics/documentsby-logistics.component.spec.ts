import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentsbyLogisticsComponent } from './documentsby-logistics.component';

describe('DocumentsbyLogisticsComponent', () => {
  let component: DocumentsbyLogisticsComponent;
  let fixture: ComponentFixture<DocumentsbyLogisticsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DocumentsbyLogisticsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentsbyLogisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
