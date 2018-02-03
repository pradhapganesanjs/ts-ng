import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectMatComponent } from './select-mat.component';

describe('SelectMatComponent', () => {
  let component: SelectMatComponent;
  let fixture: ComponentFixture<SelectMatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectMatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectMatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
