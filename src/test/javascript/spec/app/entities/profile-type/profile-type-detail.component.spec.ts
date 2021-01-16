import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KmptDemoTestModule } from '../../../test.module';
import { ProfileTypeDetailComponent } from 'app/entities/profile-type/profile-type-detail.component';
import { ProfileType } from 'app/shared/model/profile-type.model';

describe('Component Tests', () => {
  describe('ProfileType Management Detail Component', () => {
    let comp: ProfileTypeDetailComponent;
    let fixture: ComponentFixture<ProfileTypeDetailComponent>;
    const route = ({ data: of({ profileType: new ProfileType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KmptDemoTestModule],
        declarations: [ProfileTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProfileTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfileTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.profileType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
