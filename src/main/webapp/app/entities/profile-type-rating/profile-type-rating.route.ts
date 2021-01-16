import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProfileTypeRating } from 'app/shared/model/profile-type-rating.model';
import { ProfileTypeRatingService } from './profile-type-rating.service';
import { ProfileTypeRatingComponent } from './profile-type-rating.component';
import { ProfileTypeRatingDetailComponent } from './profile-type-rating-detail.component';
import { ProfileTypeRatingUpdateComponent } from './profile-type-rating-update.component';
import { IProfileTypeRating } from 'app/shared/model/profile-type-rating.model';

@Injectable({ providedIn: 'root' })
export class ProfileTypeRatingResolve implements Resolve<IProfileTypeRating> {
  constructor(private service: ProfileTypeRatingService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProfileTypeRating> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((profileTypeRating: HttpResponse<ProfileTypeRating>) => profileTypeRating.body));
    }
    return of(new ProfileTypeRating());
  }
}

export const profileTypeRatingRoute: Routes = [
  {
    path: '',
    component: ProfileTypeRatingComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'kmptDemoApp.profileTypeRating.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfileTypeRatingDetailComponent,
    resolve: {
      profileTypeRating: ProfileTypeRatingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kmptDemoApp.profileTypeRating.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfileTypeRatingUpdateComponent,
    resolve: {
      profileTypeRating: ProfileTypeRatingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kmptDemoApp.profileTypeRating.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfileTypeRatingUpdateComponent,
    resolve: {
      profileTypeRating: ProfileTypeRatingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kmptDemoApp.profileTypeRating.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
