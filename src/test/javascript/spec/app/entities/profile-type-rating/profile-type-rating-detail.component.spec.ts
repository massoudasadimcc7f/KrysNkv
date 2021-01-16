import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KmptDemoTestModule } from '../../../test.module';
import { ProfileTypeRatingDetailComponent } from 'app/entities/profile-type-rating/profile-type-rating-detail.component';
import { ProfileTypeRating } from 'app/shared/model/profile-type-rating.model';

describe('Component Tests', () => {
  describe('ProfileTypeRating Management Detail Component', () => {
    let comp: ProfileTypeRatingDetailComponent;
    let fixture: ComponentFixture<ProfileTypeRatingDetailComponent>;
    const route = ({ data: of({ profileTypeRating: new ProfileTypeRating(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KmptDemoTestModule],
        declarations: [ProfileTypeRatingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProfileTypeRatingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfileTypeRatingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.profileTypeRating).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
