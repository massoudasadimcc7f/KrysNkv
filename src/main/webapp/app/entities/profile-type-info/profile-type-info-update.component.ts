import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IProfileTypeInfo, ProfileTypeInfo } from 'app/shared/model/profile-type-info.model';
import { ProfileTypeInfoService } from './profile-type-info.service';
import { IProfileType } from 'app/shared/model/profile-type.model';
import { ProfileTypeService } from 'app/entities/profile-type/profile-type.service';
import { IProfileVariant } from 'app/shared/model/profile-variant.model';
import { ProfileVariantService } from 'app/entities/profile-variant/profile-variant.service';

@Component({
  selector: 'jhi-profile-type-info-update',
  templateUrl: './profile-type-info-update.component.html'
})
export class ProfileTypeInfoUpdateComponent implements OnInit {
  isSaving: boolean;

  profiletypes: IProfileType[];

  profilevariants: IProfileVariant[];

  editForm = this.fb.group({
    id: [],
    chapter: [],
    rank: [],
    h1: [],
    h2: [],
    content: [],
    profileTypeId: [],
    profileVariantId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected profileTypeInfoService: ProfileTypeInfoService,
    protected profileTypeService: ProfileTypeService,
    protected profileVariantService: ProfileVariantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ profileTypeInfo }) => {
      this.updateForm(profileTypeInfo);
    });
    this.profileTypeService
      .query()
      .subscribe(
        (res: HttpResponse<IProfileType[]>) => (this.profiletypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.profileVariantService
      .query()
      .subscribe(
        (res: HttpResponse<IProfileVariant[]>) => (this.profilevariants = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(profileTypeInfo: IProfileTypeInfo) {
    this.editForm.patchValue({
      id: profileTypeInfo.id,
      chapter: profileTypeInfo.chapter,
      rank: profileTypeInfo.rank,
      h1: profileTypeInfo.h1,
      h2: profileTypeInfo.h2,
      content: profileTypeInfo.content,
      profileTypeId: profileTypeInfo.profileTypeId,
      profileVariantId: profileTypeInfo.profileVariantId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const profileTypeInfo = this.createFromForm();
    if (profileTypeInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.profileTypeInfoService.update(profileTypeInfo));
    } else {
      this.subscribeToSaveResponse(this.profileTypeInfoService.create(profileTypeInfo));
    }
  }

  private createFromForm(): IProfileTypeInfo {
    return {
      ...new ProfileTypeInfo(),
      id: this.editForm.get(['id']).value,
      chapter: this.editForm.get(['chapter']).value,
      rank: this.editForm.get(['rank']).value,
      h1: this.editForm.get(['h1']).value,
      h2: this.editForm.get(['h2']).value,
      content: this.editForm.get(['content']).value,
      profileTypeId: this.editForm.get(['profileTypeId']).value,
      profileVariantId: this.editForm.get(['profileVariantId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfileTypeInfo>>) {
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

  trackProfileVariantById(index: number, item: IProfileVariant) {
    return item.id;
  }
}
