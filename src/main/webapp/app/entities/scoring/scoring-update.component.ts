import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IScoring, Scoring } from 'app/shared/model/scoring.model';
import { ScoringService } from './scoring.service';
import { IProfileVariant } from 'app/shared/model/profile-variant.model';
import { ProfileVariantService } from 'app/entities/profile-variant/profile-variant.service';

@Component({
  selector: 'jhi-scoring-update',
  templateUrl: './scoring-update.component.html'
})
export class ScoringUpdateComponent implements OnInit {
  isSaving: boolean;

  profilevariants: IProfileVariant[];

  editForm = this.fb.group({
    id: [],
    score1: [],
    score2: [],
    profileVariantId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected scoringService: ScoringService,
    protected profileVariantService: ProfileVariantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ scoring }) => {
      this.updateForm(scoring);
    });
    this.profileVariantService
      .query()
      .subscribe(
        (res: HttpResponse<IProfileVariant[]>) => (this.profilevariants = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(scoring: IScoring) {
    this.editForm.patchValue({
      id: scoring.id,
      score1: scoring.score1,
      score2: scoring.score2,
      profileVariantId: scoring.profileVariantId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const scoring = this.createFromForm();
    if (scoring.id !== undefined) {
      this.subscribeToSaveResponse(this.scoringService.update(scoring));
    } else {
      this.subscribeToSaveResponse(this.scoringService.create(scoring));
    }
  }

  private createFromForm(): IScoring {
    return {
      ...new Scoring(),
      id: this.editForm.get(['id']).value,
      score1: this.editForm.get(['score1']).value,
      score2: this.editForm.get(['score2']).value,
      profileVariantId: this.editForm.get(['profileVariantId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScoring>>) {
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
}
