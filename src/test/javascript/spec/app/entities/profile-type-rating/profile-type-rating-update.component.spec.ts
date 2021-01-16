import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KmptDemoTestModule } from '../../../test.module';
import { ProfileTypeRatingUpdateComponent } from 'app/entities/profile-type-rating/profile-type-rating-update.component';
import { ProfileTypeRatingService } from 'app/entities/profile-type-rating/profile-type-rating.service';
import { ProfileTypeRating } from 'app/shared/model/profile-type-rating.model';

describe('Component Tests', () => {
  describe('ProfileTypeRating Management Update Component', () => {
    let comp: ProfileTypeRatingUpdateComponent;
    let fixture: ComponentFixture<ProfileTypeRatingUpdateComponent>;
    let service: ProfileTypeRatingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KmptDemoTestModule],
        declarations: [ProfileTypeRatingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProfileTypeRatingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfileTypeRatingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfileTypeRatingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProfileTypeRating(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProfileTypeRating();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
