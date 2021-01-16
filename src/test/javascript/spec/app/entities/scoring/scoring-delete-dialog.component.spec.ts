import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KmptDemoTestModule } from '../../../test.module';
import { ScoringDeleteDialogComponent } from 'app/entities/scoring/scoring-delete-dialog.component';
import { ScoringService } from 'app/entities/scoring/scoring.service';

describe('Component Tests', () => {
  describe('Scoring Management Delete Component', () => {
    let comp: ScoringDeleteDialogComponent;
    let fixture: ComponentFixture<ScoringDeleteDialogComponent>;
    let service: ScoringService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [KmptDemoTestModule],
        declarations: [ScoringDeleteDialogComponent]
      })
        .overrideTemplate(ScoringDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScoringDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScoringService);
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
