import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportFetchComponent } from './report-fetch.component';

describe('ReportFetchComponent', () => {
  let component: ReportFetchComponent;
  let fixture: ComponentFixture<ReportFetchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportFetchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportFetchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
