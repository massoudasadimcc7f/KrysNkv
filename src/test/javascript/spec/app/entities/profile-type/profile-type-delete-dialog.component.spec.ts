import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KmptDemoTestModule } from '../../../test.module';
import { ProfileTypeDeleteDialogComponent } from 'app/entities/profile-type/profile-type-delete-dialog.component';
import { ProfileTypeService } from 'app/entities/profile-type/profile-type.service';

describe('Component Tests', () => {
  describe('ProfileType Management Delete Component', () => {
    let comp: ProfileTypeDeleteDialogComponent;
    let fixture: ComponentFixture<ProfileTypeDeleteDialogComponent>;
    let service: ProfileTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KmptDemoTestModule],
        declarations: [ProfileTypeDeleteDialogComponent]
      })
        .overrideTemplate(ProfileTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfileTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfileTypeService);
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
