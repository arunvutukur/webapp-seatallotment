import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Seatalloted } from 'app/shared/model/seatalloted.model';
import { SeatallotedService } from './seatalloted.service';
import { SeatallotedComponent } from './seatalloted.component';
import { SeatallotedDetailComponent } from './seatalloted-detail.component';
import { SeatallotedUpdateComponent } from './seatalloted-update.component';
import { SeatallotedDeletePopupComponent } from './seatalloted-delete-dialog.component';
import { ISeatalloted } from 'app/shared/model/seatalloted.model';
import { RequestComponent } from './request';

@Injectable({ providedIn: 'root' })
export class SeatallotedResolve implements Resolve<ISeatalloted> {
    constructor(private service: SeatallotedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((seatalloted: HttpResponse<Seatalloted>) => seatalloted.body));
        }
        return of(new Seatalloted());
    }
}

export const seatallotedRoute: Routes = [
    {
        path: 'seatalloted',
        component: SeatallotedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Seatalloteds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'seatalloted/:id/view',
        component: SeatallotedDetailComponent,
        resolve: {
            seatalloted: SeatallotedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Seatalloteds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'seatalloted/new',
        component: SeatallotedUpdateComponent,
        resolve: {
            seatalloted: SeatallotedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Seatalloteds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'seatalloted/:id/edit',
        component: SeatallotedUpdateComponent,
        resolve: {
            seatalloted: SeatallotedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Seatalloteds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'seatalloted/:id/request',
        component: RequestComponent,
        resolve: {
            seatalloted: SeatallotedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Seatalloteds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seatallotedPopupRoute: Routes = [
    {
        path: 'seatalloted/:id/delete',
        component: SeatallotedDeletePopupComponent,
        resolve: {
            seatalloted: SeatallotedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Seatalloteds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
