import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SeatallocationSharedModule } from 'app/shared';
import {
    AccessComponent,
    AccessDetailComponent,
    AccessUpdateComponent,
    AccessDeletePopupComponent,
    AccessDeleteDialogComponent,
    accessRoute,
    accessPopupRoute
} from './';

const ENTITY_STATES = [...accessRoute, ...accessPopupRoute];

@NgModule({
    imports: [SeatallocationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AccessComponent, AccessDetailComponent, AccessUpdateComponent, AccessDeleteDialogComponent, AccessDeletePopupComponent],
    entryComponents: [AccessComponent, AccessUpdateComponent, AccessDeleteDialogComponent, AccessDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeatallocationAccessModule {}
