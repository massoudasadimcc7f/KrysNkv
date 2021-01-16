import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProfileTypeInfo } from 'app/shared/model/profile-type-info.model';
import { ProfileTypeInfoService } from './profile-type-info.service';
import { ProfileTypeInfoComponent } from './profile-type-info.component';
import { ProfileTypeInfoDetailComponent } from './profile-type-info-detail.component';
import { ProfileTypeInfoUpdateComponent } from './profile-type-info-update.component';
import { IProfileTypeInfo } from 'app/shared/model/profile-type-info.model';

@Injectable({ providedIn: 'root' })
export class ProfileTypeInfoResolve implements Resolve<IProfileTypeInfo> {
  constructor(private service: ProfileTypeInfoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProfileTypeInfo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((profileTypeInfo: HttpResponse<ProfileTypeInfo>) => profileTypeInfo.body));
    }
    return of(new ProfileTypeInfo());
  }
}

export const profileTypeInfoRoute: Routes = [
  {
    path: '',
    component: ProfileTypeInfoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'kmptDemoApp.profileTypeInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfileTypeInfoDetailComponent,
    resolve: {
      profileTypeInfo: ProfileTypeInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kmptDemoApp.profileTypeInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfileTypeInfoUpdateComponent,
    resolve: {
      profileTypeInfo: ProfileTypeInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kmptDemoApp.profileTypeInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfileTypeInfoUpdateComponent,
    resolve: {
      profileTypeInfo: ProfileTypeInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kmptDemoApp.profileTypeInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
