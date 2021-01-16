import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfileTypeRating } from 'app/shared/model/profile-type-rating.model';
import { ProfileTypeRatingService } from './profile-type-rating.service';

@Component({
  templateUrl: './profile-type-rating-delete-dialog.component.html'
})
export class ProfileTypeRatingDeleteDialogComponent {
  profileTypeRating: IProfileTypeRating;

  constructor(
    protected profileTypeRatingService: ProfileTypeRatingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.profileTypeRatingService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'profileTypeRatingListModification',
        content: 'Deleted an profileTypeRating'
      });
      this.activeModal.dismiss(true);
    });
  }
}
