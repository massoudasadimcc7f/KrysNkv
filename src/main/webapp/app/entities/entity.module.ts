import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'question',
        loadChildren: () => import('./question/question.module').then(m => m.KmptDemoQuestionModule)
      },
      {
        path: 'profile',
        loadChildren: () => import('./profile/profile.module').then(m => m.KmptDemoProfileModule)
      },
      {
        path: 'profile-type',
        loadChildren: () => import('./profile-type/profile-type.module').then(m => m.KmptDemoProfileTypeModule)
      },
      {
        path: 'profile-type-info',
        loadChildren: () => import('./profile-type-info/profile-type-info.module').then(m => m.KmptDemoProfileTypeInfoModule)
      },
      {
        path: 'profile-type-rating',
        loadChildren: () => import('./profile-type-rating/profile-type-rating.module').then(m => m.KmptDemoProfileTypeRatingModule)
      },
      {
        path: 'profile-variant',
        loadChildren: () => import('./profile-variant/profile-variant.module').then(m => m.KmptDemoProfileVariantModule)
      },
      {
        path: 'scoring',
        loadChildren: () => import('./scoring/scoring.module').then(m => m.KmptDemoScoringModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class KmptDemoEntityModule {}
