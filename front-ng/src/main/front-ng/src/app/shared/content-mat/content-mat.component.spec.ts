import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentMatComponent } from './content-mat.component';

describe('ContentMatComponent', () => {
  let component: ContentMatComponent;
  let fixture: ComponentFixture<ContentMatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContentMatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentMatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
