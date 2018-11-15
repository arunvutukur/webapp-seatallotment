import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SeatallocationSharedModule } from 'app/shared';
import {
    SeatallotedComponent,
    SeatallotedDetailComponent,
    SeatallotedUpdateComponent,
    SeatallotedDeletePopupComponent,
    SeatallotedDeleteDialogComponent,
    seatallotedRoute,
    seatallotedPopupRoute
} from './';
import { RequestComponent } from './request';

const ENTITY_STATES = [...seatallotedRoute, ...seatallotedPopupRoute];

@NgModule({
    imports: [SeatallocationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SeatallotedComponent,
        SeatallotedDetailComponent,
        SeatallotedUpdateComponent,
        SeatallotedDeleteDialogComponent,
        SeatallotedDeletePopupComponent,
        RequestComponent
    ],
    entryComponents: [SeatallotedComponent, SeatallotedUpdateComponent, SeatallotedDeleteDialogComponent, SeatallotedDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeatallocationSeatallotedModule {}
