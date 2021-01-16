import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfileTypeRating } from 'app/shared/model/profile-type-rating.model';

@Component({
  selector: 'jhi-profile-type-rating-detail',
  templateUrl: './profile-type-rating-detail.component.html'
})
export class ProfileTypeRatingDetailComponent implements OnInit {
  profileTypeRating: IProfileTypeRating;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ profileTypeRating }) => {
      this.profileTypeRating = profileTypeRating;
    });
  }

  previousState() {
    window.history.back();
  }
}
