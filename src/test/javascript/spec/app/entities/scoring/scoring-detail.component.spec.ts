import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KmptDemoTestModule } from '../../../test.module';
import { ScoringDetailComponent } from 'app/entities/scoring/scoring-detail.component';
import { Scoring } from 'app/shared/model/scoring.model';

describe('Component Tests', () => {
  describe('Scoring Management Detail Component', () => {
    let comp: ScoringDetailComponent;
    let fixture: ComponentFixture<ScoringDetailComponent>;
    const route = ({ data: of({ scoring: new Scoring(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KmptDemoTestModule],
        declarations: [ScoringDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ScoringDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScoringDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.scoring).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
