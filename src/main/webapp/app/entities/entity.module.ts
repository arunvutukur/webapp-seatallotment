import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SeatallocationSeatallotedModule } from './seatalloted/seatalloted.module';
import { SeatallocationAccessModule } from './access/access.module';
import { SeatallocationEmployeeModule } from './employee/employee.module';
import { SeatallotedService } from './seatalloted';
import { UploadFileService } from './upload-file.service';

/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SeatallocationSeatallotedModule,
        SeatallocationAccessModule,
        SeatallocationEmployeeModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [SeatallotedService, UploadFileService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeatallocationEntityModule {}
