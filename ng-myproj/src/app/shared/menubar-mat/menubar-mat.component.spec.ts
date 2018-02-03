import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MenubarMatComponent } from './menubar-mat.component';

describe('MenubarMatComponent', () => {
  let component: MenubarMatComponent;
  let fixture: ComponentFixture<MenubarMatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MenubarMatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenubarMatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
