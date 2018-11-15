import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SeatallocationSeatallotedModule } from './seatalloted/seatalloted.module';
import { SeatallocationAccessModule } from './access/access.module';
import { SeatallocationEmployeeModule } from './employee/employee.module';
import { SeatallotedService } from './seatalloted';
import { FormUploadComponent } from './form-upload/form-upload.component';

/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SeatallocationSeatallotedModule,
        SeatallocationAccessModule,
        SeatallocationEmployeeModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [FormUploadComponent, FormUploadComponent],
    entryComponents: [],
    providers: [SeatallotedService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeatallocationEntityModule {}
