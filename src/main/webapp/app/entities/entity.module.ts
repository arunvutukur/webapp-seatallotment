import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SeatallocationSeatallotedModule } from './seatalloted/seatalloted.module';
import { SeatallocationAccessModule } from './access/access.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SeatallocationSeatallotedModule,
        SeatallocationAccessModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeatallocationEntityModule {}
