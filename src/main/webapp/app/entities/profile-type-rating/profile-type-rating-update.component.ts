import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IProfileTypeRating, ProfileTypeRating } from 'app/shared/model/profile-type-rating.model';
import { ProfileTypeRatingService } from './profile-type-rating.service';
import { IProfileType } from 'app/shared/model/profile-type.model';
import { ProfileTypeService } from 'app/entities/profile-type/profile-type.service';

@Component({
  selector: 'jhi-profile-type-rating-update',
  templateUrl: './profile-type-rating-update.component.html'
})
export class ProfileTypeRatingUpdateComponent implements OnInit {
  isSaving: boolean;

  profiletypes: IProfileType[];

  editForm = this.fb.group({
    id: [],
    characteristic: [],
    rating: [],
    profileTypeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected profileTypeRatingService: ProfileTypeRatingService,
    protected profileTypeService: ProfileTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ profileTypeRating }) => {
      this.updateForm(profileTypeRating);
    });
    this.profileTypeService
      .query()
      .subscribe(
        (res: HttpResponse<IProfileType[]>) => (this.profiletypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(profileTypeRating: IProfileTypeRating) {
    this.editForm.patchValue({
      id: profileTypeRating.id,
      characteristic: profileTypeRating.characteristic,
      rating: profileTypeRating.rating,
      profileTypeId: profileTypeRating.profileTypeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const profileTypeRating = this.createFromForm();
    if (profileTypeRating.id !== undefined) {
      this.subscribeToSaveResponse(this.profileTypeRatingService.update(profileTypeRating));
    } else {
      this.subscribeToSaveResponse(this.profileTypeRatingService.create(profileTypeRating));
    }
  }

  private createFromForm(): IProfileTypeRating {
    return {
      ...new ProfileTypeRating(),
      id: this.editForm.get(['id']).value,
      characteristic: this.editForm.get(['characteristic']).value,
      rating: this.editForm.get(['rating']).value,
      profileTypeId: this.editForm.get(['profileTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfileTypeRating>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProfileTypeById(index: number, item: IProfileType) {
    return item.id;
  }
}
