import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProfileType, ProfileType } from 'app/shared/model/profile-type.model';
import { ProfileTypeService } from './profile-type.service';

@Component({
  selector: 'jhi-profile-type-update',
  templateUrl: './profile-type-update.component.html'
})
export class ProfileTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: []
  });

  constructor(protected profileTypeService: ProfileTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ profileType }) => {
      this.updateForm(profileType);
    });
  }

  updateForm(profileType: IProfileType) {
    this.editForm.patchValue({
      id: profileType.id,
      name: profileType.name,
      description: profileType.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const profileType = this.createFromForm();
    if (profileType.id !== undefined) {
      this.subscribeToSaveResponse(this.profileTypeService.update(profileType));
    } else {
      this.subscribeToSaveResponse(this.profileTypeService.create(profileType));
    }
  }

  private createFromForm(): IProfileType {
    return {
      ...new ProfileType(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfileType>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
