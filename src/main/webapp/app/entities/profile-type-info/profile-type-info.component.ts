import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProfileTypeInfo } from 'app/shared/model/profile-type-info.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ProfileTypeInfoService } from './profile-type-info.service';
import { ProfileTypeInfoDeleteDialogComponent } from './profile-type-info-delete-dialog.component';

@Component({
  selector: 'jhi-profile-type-info',
  templateUrl: './profile-type-info.component.html'
})
export class ProfileTypeInfoComponent implements OnInit, OnDestroy {
  profileTypeInfos: IProfileTypeInfo[];
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
    protected profileTypeInfoService: ProfileTypeInfoService,
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
    this.profileTypeInfoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IProfileTypeInfo[]>) => this.paginateProfileTypeInfos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/profile-type-info'], {
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
      '/profile-type-info',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProfileTypeInfos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProfileTypeInfo) {
    return item.id;
  }

  registerChangeInProfileTypeInfos() {
    this.eventSubscriber = this.eventManager.subscribe('profileTypeInfoListModification', () => this.loadAll());
  }

  delete(profileTypeInfo: IProfileTypeInfo) {
    const modalRef = this.modalService.open(ProfileTypeInfoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.profileTypeInfo = profileTypeInfo;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateProfileTypeInfos(data: IProfileTypeInfo[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.profileTypeInfos = data;
  }
}
