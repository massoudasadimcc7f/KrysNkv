import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KmptDemoTestModule } from '../../../test.module';
import { ProfileTypeInfoDeleteDialogComponent } from 'app/entities/profile-type-info/profile-type-info-delete-dialog.component';
import { ProfileTypeInfoService } from 'app/entities/profile-type-info/profile-type-info.service';

describe('Component Tests', () => {
  describe('ProfileTypeInfo Management Delete Component', () => {
    let comp: ProfileTypeInfoDeleteDialogComponent;
    let fixture: ComponentFixture<ProfileTypeInfoDeleteDialogComponent>;
    let service: ProfileTypeInfoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KmptDemoTestModule],
        declarations: [ProfileTypeInfoDeleteDialogComponent]
      })
        .overrideTemplate(ProfileTypeInfoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfileTypeInfoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfileTypeInfoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
