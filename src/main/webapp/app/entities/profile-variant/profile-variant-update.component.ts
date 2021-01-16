import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IProfileVariant, ProfileVariant } from 'app/shared/model/profile-variant.model';
import { ProfileVariantService } from './profile-variant.service';
import { IProfileType } from 'app/shared/model/profile-type.model';
import { ProfileTypeService } from 'app/entities/profile-type/profile-type.service';

@Component({
  selector: 'jhi-profile-variant-update',
  templateUrl: './profile-variant-update.component.html'
})
export class ProfileVariantUpdateComponent implements OnInit {
  isSaving: boolean;

  profiletypes: IProfileType[];

  editForm = this.fb.group({
    id: [],
    name: [],
    color: [],
    profileTypeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected profileVariantService: ProfileVariantService,
    protected profileTypeService: ProfileTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ profileVariant }) => {
      this.updateForm(profileVariant);
    });
    this.profileTypeService
      .query()
      .subscribe(
        (res: HttpResponse<IProfileType[]>) => (this.profiletypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(profileVariant: IProfileVariant) {
    this.editForm.patchValue({
      id: profileVariant.id,
      name: profileVariant.name,
      color: profileVariant.color,
      profileTypeId: profileVariant.profileTypeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const profileVariant = this.createFromForm();
    if (profileVariant.id !== undefined) {
      this.subscribeToSaveResponse(this.profileVariantService.update(profileVariant));
    } else {
      this.subscribeToSaveResponse(this.profileVariantService.create(profileVariant));
    }
  }

  private createFromForm(): IProfileVariant {
    return {
      ...new ProfileVariant(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      color: this.editForm.get(['color']).value,
      profileTypeId: this.editForm.get(['profileTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfileVariant>>) {
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
