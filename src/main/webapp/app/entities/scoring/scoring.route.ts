import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Scoring } from 'app/shared/model/scoring.model';
import { ScoringService } from './scoring.service';
import { ScoringComponent } from './scoring.component';
import { ScoringDetailComponent } from './scoring-detail.component';
import { ScoringUpdateComponent } from './scoring-update.component';
import { IScoring } from 'app/shared/model/scoring.model';

@Injectable({ providedIn: 'root' })
export class ScoringResolve implements Resolve<IScoring> {
  constructor(private service: ScoringService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IScoring> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((scoring: HttpResponse<Scoring>) => scoring.body));
    }
    return of(new Scoring());
  }
}

export const scoringRoute: Routes = [
  {
    path: '',
    component: ScoringComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'kmptDemoApp.scoring.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ScoringDetailComponent,
    resolve: {
      scoring: ScoringResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kmptDemoApp.scoring.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ScoringUpdateComponent,
    resolve: {
      scoring: ScoringResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kmptDemoApp.scoring.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ScoringUpdateComponent,
    resolve: {
      scoring: ScoringResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kmptDemoApp.scoring.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
