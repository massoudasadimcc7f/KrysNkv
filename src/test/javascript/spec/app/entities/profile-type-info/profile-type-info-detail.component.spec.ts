import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KmptDemoTestModule } from '../../../test.module';
import { ProfileTypeInfoDetailComponent } from 'app/entities/profile-type-info/profile-type-info-detail.component';
import { ProfileTypeInfo } from 'app/shared/model/profile-type-info.model';

describe('Component Tests', () => {
  describe('ProfileTypeInfo Management Detail Component', () => {
    let comp: ProfileTypeInfoDetailComponent;
    let fixture: ComponentFixture<ProfileTypeInfoDetailComponent>;
    const route = ({ data: of({ profileTypeInfo: new ProfileTypeInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KmptDemoTestModule],
        declarations: [ProfileTypeInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProfileTypeInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfileTypeInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.profileTypeInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
