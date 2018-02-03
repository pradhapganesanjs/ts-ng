import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InputMatComponent } from './input-mat.component';

describe('InputMatComponent', () => {
  let component: InputMatComponent;
  let fixture: ComponentFixture<InputMatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InputMatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InputMatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
