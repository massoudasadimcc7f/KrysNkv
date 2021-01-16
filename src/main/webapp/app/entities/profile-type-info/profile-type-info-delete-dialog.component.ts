import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfileTypeInfo } from 'app/shared/model/profile-type-info.model';
import { ProfileTypeInfoService } from './profile-type-info.service';

@Component({
  templateUrl: './profile-type-info-delete-dialog.component.html'
})
export class ProfileTypeInfoDeleteDialogComponent {
  profileTypeInfo: IProfileTypeInfo;

  constructor(
    protected profileTypeInfoService: ProfileTypeInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.profileTypeInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'profileTypeInfoListModification',
        content: 'Deleted an profileTypeInfo'
      });
      this.activeModal.dismiss(true);
    });
  }
}
