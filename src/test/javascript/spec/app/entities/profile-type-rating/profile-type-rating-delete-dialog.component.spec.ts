import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KmptDemoTestModule } from '../../../test.module';
import { ProfileTypeRatingDeleteDialogComponent } from 'app/entities/profile-type-rating/profile-type-rating-delete-dialog.component';
import { ProfileTypeRatingService } from 'app/entities/profile-type-rating/profile-type-rating.service';

describe('Component Tests', () => {
  describe('ProfileTypeRating Management Delete Component', () => {
    let comp: ProfileTypeRatingDeleteDialogComponent;
    let fixture: ComponentFixture<ProfileTypeRatingDeleteDialogComponent>;
    let service: ProfileTypeRatingService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KmptDemoTestModule],
        declarations: [ProfileTypeRatingDeleteDialogComponent]
      })
        .overrideTemplate(ProfileTypeRatingDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfileTypeRatingDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfileTypeRatingService);
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
