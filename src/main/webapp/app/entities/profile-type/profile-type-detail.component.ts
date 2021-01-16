import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfileType } from 'app/shared/model/profile-type.model';

@Component({
  selector: 'jhi-profile-type-detail',
  templateUrl: './profile-type-detail.component.html'
})
export class ProfileTypeDetailComponent implements OnInit {
  profileType: IProfileType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ profileType }) => {
      this.profileType = profileType;
    });
  }

  previousState() {
    window.history.back();
  }
}
