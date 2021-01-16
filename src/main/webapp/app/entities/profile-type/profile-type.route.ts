import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProfileType } from 'app/shared/model/profile-type.model';
import { ProfileTypeService } from './profile-type.service';
import { ProfileTypeComponent } from './profile-type.component';
import { ProfileTypeDetailComponent } from './profile-type-detail.component';
import { ProfileTypeUpdateComponent } from './profile-type-update.component';
import { IProfileType } from 'app/shared/model/profile-type.model';

@Injectable({ providedIn: 'root' })
export class ProfileTypeResolve implements Resolve<IProfileType> {
  constructor(private service: ProfileTypeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProfileType> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((profileType: HttpResponse<ProfileType>) => profileType.body));
    }
    return of(new ProfileType());
  }
}

export const profileTypeRoute: Routes = [
  {
    path: '',
    component: ProfileTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'kmptDemoApp.profileType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfileTypeDetailComponent,
    resolve: {
      profileType: ProfileTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kmptDemoApp.profileType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfileTypeUpdateComponent,
    resolve: {
      profileType: ProfileTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kmptDemoApp.profileType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfileTypeUpdateComponent,
    resolve: {
      profileType: ProfileTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'kmptDemoApp.profileType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
