import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InlineConfirmComponent } from './inline-confirm.component';

describe('InlineConfirmComponent', () => {
  let component: InlineConfirmComponent;
  let fixture: ComponentFixture<InlineConfirmComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InlineConfirmComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InlineConfirmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
