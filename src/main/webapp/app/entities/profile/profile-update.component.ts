import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IProfile, Profile } from 'app/shared/model/profile.model';
import { ProfileService } from './profile.service';
import { IProfileVariant } from 'app/shared/model/profile-variant.model';
import { ProfileVariantService } from 'app/entities/profile-variant/profile-variant.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question/question.service';

@Component({
  selector: 'jhi-profile-update',
  templateUrl: './profile-update.component.html'
})
export class ProfileUpdateComponent implements OnInit {
  isSaving: boolean;

  profilevariants: IProfileVariant[];

  users: IUser[];

  questions: IQuestion[];

  editForm = this.fb.group({
    id: [],
    name: [],
    jobTitle: [],
    notes: [],
    me: [],
    relation: [],
    profileVariantId: [],
    userId: [],
    questions: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected profileService: ProfileService,
    protected profileVariantService: ProfileVariantService,
    protected userService: UserService,
    protected questionService: QuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ profile }) => {
      this.updateForm(profile);
    });
    this.profileVariantService
      .query()
      .subscribe(
        (res: HttpResponse<IProfileVariant[]>) => (this.profilevariants = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.questionService
      .query()
      .subscribe((res: HttpResponse<IQuestion[]>) => (this.questions = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(profile: IProfile) {
    this.editForm.patchValue({
      id: profile.id,
      name: profile.name,
      jobTitle: profile.jobTitle,
      notes: profile.notes,
      me: profile.me,
      relation: profile.relation,
      profileVariantId: profile.profileVariantId,
      userId: profile.userId,
      questions: profile.questions
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const profile = this.createFromForm();
    if (profile.id !== undefined) {
      this.subscribeToSaveResponse(this.profileService.update(profile));
    } else {
      this.subscribeToSaveResponse(this.profileService.create(profile));
    }
  }

  private createFromForm(): IProfile {
    return {
      ...new Profile(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      jobTitle: this.editForm.get(['jobTitle']).value,
      notes: this.editForm.get(['notes']).value,
      me: this.editForm.get(['me']).value,
      relation: this.editForm.get(['relation']).value,
      profileVariantId: this.editForm.get(['profileVariantId']).value,
      userId: this.editForm.get(['userId']).value,
      questions: this.editForm.get(['questions']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfile>>) {
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

  trackProfileVariantById(index: number, item: IProfileVariant) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackQuestionById(index: number, item: IQuestion) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
