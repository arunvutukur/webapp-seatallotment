import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ISeatalloted } from 'app/shared/model/seatalloted.model';
import { Principal } from 'app/core';
import { ITEMS_PER_PAGE } from 'app/shared';
import { SeatallotedService } from './seatalloted.service';

@Component({
    selector: 'jhi-seatalloted',
    templateUrl: './seatalloted.component.html'
})
export class SeatallotedComponent implements OnInit, OnDestroy {
    currentAccount: any;
    seatalloteds: ISeatalloted[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    isSaving: boolean;
    predicate: any;
    previousPage: any;
    reverse: any;
    seatalloted: ISeatalloted;
    requestForApproval: boolean;
    constructor(
        private seatallotedService: SeatallotedService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.requestForApproval = false;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSeatalloteds();
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ seatalloted }) => {
            this.seatalloted = seatalloted;
        });
    }

    loadAll() {
        this.seatallotedService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ISeatalloted[]>) => this.paginateSeatalloteds(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/seatalloted'], {
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
            '/seatalloted',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISeatalloted) {
        return item.id;
    }

    registerChangeInSeatalloteds() {
        this.eventSubscriber = this.eventManager.subscribe('seatallotedListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateSeatalloteds(data: ISeatalloted[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.seatalloteds = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
    updateRequested(requestForApproval: boolean) {
        this.subscribeToSaveResponse(this.seatallotedService.updateRequestForApproval(this.seatalloted, requestForApproval));
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISeatalloted>>) {
        result.subscribe((res: HttpResponse<ISeatalloted>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = true;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    previousState() {
        window.history.back();
    }
}
