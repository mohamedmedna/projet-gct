import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifierChampsComponent } from './modifier-champs.component';

describe('ModifierChampsComponent', () => {
  let component: ModifierChampsComponent;
  let fixture: ComponentFixture<ModifierChampsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifierChampsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModifierChampsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
