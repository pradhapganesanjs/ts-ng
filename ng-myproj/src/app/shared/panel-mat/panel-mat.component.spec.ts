import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelMatComponent } from './panel-mat.component';

describe('PanelMatComponent', () => {
  let component: PanelMatComponent;
  let fixture: ComponentFixture<PanelMatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PanelMatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PanelMatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
