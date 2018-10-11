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

const ENTITY_STATES = [...seatallotedRoute, ...seatallotedPopupRoute];

@NgModule({
    imports: [SeatallocationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SeatallotedComponent,
        SeatallotedDetailComponent,
        SeatallotedUpdateComponent,
        SeatallotedDeleteDialogComponent,
        SeatallotedDeletePopupComponent
    ],
    entryComponents: [SeatallotedComponent, SeatallotedUpdateComponent, SeatallotedDeleteDialogComponent, SeatallotedDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeatallocationSeatallotedModule {}
