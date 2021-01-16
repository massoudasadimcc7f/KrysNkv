import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProfileTypeRating } from 'app/shared/model/profile-type-rating.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ProfileTypeRatingService } from './profile-type-rating.service';
import { ProfileTypeRatingDeleteDialogComponent } from './profile-type-rating-delete-dialog.component';

@Component({
  selector: 'jhi-profile-type-rating',
  templateUrl: './profile-type-rating.component.html'
})
export class ProfileTypeRatingComponent implements OnInit, OnDestroy {
  profileTypeRatings: IProfileTypeRating[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    protected profileTypeRatingService: ProfileTypeRatingService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.profileTypeRatingService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IProfileTypeRating[]>) => this.paginateProfileTypeRatings(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/profile-type-rating'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/profile-type-rating',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProfileTypeRatings();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProfileTypeRating) {
    return item.id;
  }

  registerChangeInProfileTypeRatings() {
    this.eventSubscriber = this.eventManager.subscribe('profileTypeRatingListModification', () => this.loadAll());
  }

  delete(profileTypeRating: IProfileTypeRating) {
    const modalRef = this.modalService.open(ProfileTypeRatingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.profileTypeRating = profileTypeRating;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateProfileTypeRatings(data: IProfileTypeRating[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.profileTypeRatings = data;
  }
}
