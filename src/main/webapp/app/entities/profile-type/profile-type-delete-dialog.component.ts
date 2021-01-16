import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfileType } from 'app/shared/model/profile-type.model';
import { ProfileTypeService } from './profile-type.service';

@Component({
  templateUrl: './profile-type-delete-dialog.component.html'
})
export class ProfileTypeDeleteDialogComponent {
  profileType: IProfileType;

  constructor(
    protected profileTypeService: ProfileTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.profileTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'profileTypeListModification',
        content: 'Deleted an profileType'
      });
      this.activeModal.dismiss(true);
    });
  }
}
