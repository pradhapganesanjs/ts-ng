import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportLimitComponent } from './report-limit.component';

describe('ReportLimitComponent', () => {
  let component: ReportLimitComponent;
  let fixture: ComponentFixture<ReportLimitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportLimitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportLimitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
