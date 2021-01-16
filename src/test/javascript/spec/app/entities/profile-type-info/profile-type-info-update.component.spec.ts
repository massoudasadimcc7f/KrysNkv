import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KmptDemoTestModule } from '../../../test.module';
import { ProfileTypeInfoUpdateComponent } from 'app/entities/profile-type-info/profile-type-info-update.component';
import { ProfileTypeInfoService } from 'app/entities/profile-type-info/profile-type-info.service';
import { ProfileTypeInfo } from 'app/shared/model/profile-type-info.model';

describe('Component Tests', () => {
  describe('ProfileTypeInfo Management Update Component', () => {
    let comp: ProfileTypeInfoUpdateComponent;
    let fixture: ComponentFixture<ProfileTypeInfoUpdateComponent>;
    let service: ProfileTypeInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KmptDemoTestModule],
        declarations: [ProfileTypeInfoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProfileTypeInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfileTypeInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfileTypeInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProfileTypeInfo(123);
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
        const entity = new ProfileTypeInfo();
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
