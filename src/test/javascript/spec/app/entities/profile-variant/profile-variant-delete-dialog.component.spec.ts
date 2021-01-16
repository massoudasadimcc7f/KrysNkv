import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KmptDemoTestModule } from '../../../test.module';
import { ProfileVariantDeleteDialogComponent } from 'app/entities/profile-variant/profile-variant-delete-dialog.component';
import { ProfileVariantService } from 'app/entities/profile-variant/profile-variant.service';

describe('Component Tests', () => {
  describe('ProfileVariant Management Delete Component', () => {
    let comp: ProfileVariantDeleteDialogComponent;
    let fixture: ComponentFixture<ProfileVariantDeleteDialogComponent>;
    let service: ProfileVariantService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KmptDemoTestModule],
        declarations: [ProfileVariantDeleteDialogComponent]
      })
        .overrideTemplate(ProfileVariantDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfileVariantDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfileVariantService);
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
