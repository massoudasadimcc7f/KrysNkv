import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProfileVariant } from 'app/shared/model/profile-variant.model';
import { ProfileVariantService } from './profile-variant.service';
import { ProfileVariantComponent } from './profile-variant.component';
import { ProfileVariantDetailComponent } from './profile-variant-detail.component';
import { ProfileVariantUpdateComponent } from './profile-variant-update.component';
import { IProfileVariant } from 'app/shared/model/profile-variant.model';

@Injectable({ providedIn: 'root' })
export class ProfileVariantResolve implements Resolve<IProfileVariant> {
  constructor(private service: ProfileVariantService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProfileVariant> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((profileVariant: HttpResponse<ProfileVariant>) => profileVariant.body));
    }
    return of(new ProfileVariant());
  }
}

export const profileVariantRoute: Routes = [
  {
    path: '',
    component: ProfileVariantComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'kmptDemoApp.profileVariant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfileVariantDetailComponent,
    resolve: {
      profileVariant: ProfileVariantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kmptDemoApp.profileVariant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfileVariantUpdateComponent,
    resolve: {
      profileVariant: ProfileVariantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kmptDemoApp.profileVariant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfileVariantUpdateComponent,
    resolve: {
      profileVariant: ProfileVariantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kmptDemoApp.profileVariant.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
