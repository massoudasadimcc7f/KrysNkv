import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KmptDemoSharedModule } from 'app/shared/shared.module';
import { ProfileTypeComponent } from './profile-type.component';
import { ProfileTypeDetailComponent } from './profile-type-detail.component';
import { ProfileTypeUpdateComponent } from './profile-type-update.component';
import { ProfileTypeDeleteDialogComponent } from './profile-type-delete-dialog.component';
import { profileTypeRoute } from './profile-type.route';

@NgModule({
  imports: [KmptDemoSharedModule, RouterModule.forChild(profileTypeRoute)],
  declarations: [ProfileTypeComponent, ProfileTypeDetailComponent, ProfileTypeUpdateComponent, ProfileTypeDeleteDialogComponent],
  entryComponents: [ProfileTypeDeleteDialogComponent]
})
export class KmptDemoProfileTypeModule {}
