import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfileTypeInfo } from 'app/shared/model/profile-type-info.model';

@Component({
  selector: 'jhi-profile-type-info-detail',
  templateUrl: './profile-type-info-detail.component.html'
})
export class ProfileTypeInfoDetailComponent implements OnInit {
  profileTypeInfo: IProfileTypeInfo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ profileTypeInfo }) => {
      this.profileTypeInfo = profileTypeInfo;
    });
  }

  previousState() {
    window.history.back();
  }
}
