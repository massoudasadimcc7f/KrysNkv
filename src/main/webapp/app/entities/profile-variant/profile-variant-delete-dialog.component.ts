import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfileVariant } from 'app/shared/model/profile-variant.model';
import { ProfileVariantService } from './profile-variant.service';

@Component({
  templateUrl: './profile-variant-delete-dialog.component.html'
})
export class ProfileVariantDeleteDialogComponent {
  profileVariant: IProfileVariant;

  constructor(
    protected profileVariantService: ProfileVariantService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.profileVariantService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'profileVariantListModification',
        content: 'Deleted an profileVariant'
      });
      this.activeModal.dismiss(true);
    });
  }
}
