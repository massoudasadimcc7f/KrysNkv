import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KmptDemoSharedModule } from 'app/shared/shared.module';
import { ProfileTypeRatingComponent } from './profile-type-rating.component';
import { ProfileTypeRatingDetailComponent } from './profile-type-rating-detail.component';
import { ProfileTypeRatingUpdateComponent } from './profile-type-rating-update.component';
import { ProfileTypeRatingDeleteDialogComponent } from './profile-type-rating-delete-dialog.component';
import { profileTypeRatingRoute } from './profile-type-rating.route';

@NgModule({
  imports: [KmptDemoSharedModule, RouterModule.forChild(profileTypeRatingRoute)],
  declarations: [
    ProfileTypeRatingComponent,
    ProfileTypeRatingDetailComponent,
    ProfileTypeRatingUpdateComponent,
    ProfileTypeRatingDeleteDialogComponent
  ],
  entryComponents: [ProfileTypeRatingDeleteDialogComponent]
})
export class KmptDemoProfileTypeRatingModule {}
