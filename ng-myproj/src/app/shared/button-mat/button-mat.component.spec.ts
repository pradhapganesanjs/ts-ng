import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonMatComponent } from './button-mat.component';

describe('ButtonMatComponent', () => {
  let component: ButtonMatComponent;
  let fixture: ComponentFixture<ButtonMatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ButtonMatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ButtonMatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
