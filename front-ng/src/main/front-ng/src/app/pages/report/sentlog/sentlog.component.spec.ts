import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SentlogComponent } from './sentlog.component';

describe('SentlogComponent', () => {
  let component: SentlogComponent;
  let fixture: ComponentFixture<SentlogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SentlogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SentlogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
