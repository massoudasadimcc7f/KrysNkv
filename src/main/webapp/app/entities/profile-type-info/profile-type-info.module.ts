import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KmptDemoSharedModule } from 'app/shared/shared.module';
import { ProfileTypeInfoComponent } from './profile-type-info.component';
import { ProfileTypeInfoDetailComponent } from './profile-type-info-detail.component';
import { ProfileTypeInfoUpdateComponent } from './profile-type-info-update.component';
import { ProfileTypeInfoDeleteDialogComponent } from './profile-type-info-delete-dialog.component';
import { profileTypeInfoRoute } from './profile-type-info.route';

@NgModule({
  imports: [KmptDemoSharedModule, RouterModule.forChild(profileTypeInfoRoute)],
  declarations: [
    ProfileTypeInfoComponent,
    ProfileTypeInfoDetailComponent,
    ProfileTypeInfoUpdateComponent,
    ProfileTypeInfoDeleteDialogComponent
  ],
  entryComponents: [ProfileTypeInfoDeleteDialogComponent]
})
export class KmptDemoProfileTypeInfoModule {}
