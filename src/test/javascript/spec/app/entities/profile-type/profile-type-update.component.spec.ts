import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { KmptDemoTestModule } from '../../../test.module';
import { ProfileTypeUpdateComponent } from 'app/entities/profile-type/profile-type-update.component';
import { ProfileTypeService } from 'app/entities/profile-type/profile-type.service';
import { ProfileType } from 'app/shared/model/profile-type.model';

describe('Component Tests', () => {
  describe('ProfileType Management Update Component', () => {
    let comp: ProfileTypeUpdateComponent;
    let fixture: ComponentFixture<ProfileTypeUpdateComponent>;
    let service: ProfileTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KmptDemoTestModule],
        declarations: [ProfileTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProfileTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfileTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfileTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProfileType(123);
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
        const entity = new ProfileType();
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
