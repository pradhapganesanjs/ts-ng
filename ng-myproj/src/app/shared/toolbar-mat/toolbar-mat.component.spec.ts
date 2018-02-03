import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ToolbarMatComponent } from './toolbar-mat.component';

describe('ToolbarMatComponent', () => {
  let component: ToolbarMatComponent;
  let fixture: ComponentFixture<ToolbarMatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ToolbarMatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ToolbarMatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
