import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SeatallocationSharedModule } from 'app/shared';
import {
    EmployeeComponent,
    EmployeeDetailComponent,
    EmployeeUpdateComponent,
    EmployeeDeletePopupComponent,
    EmployeeDeleteDialogComponent,
    employeeRoute,
    employeePopupRoute
} from './';
import { FormUploadComponent } from '../form-upload/form-upload.component';

const ENTITY_STATES = [...employeeRoute, ...employeePopupRoute];

@NgModule({
    imports: [SeatallocationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EmployeeComponent,
        EmployeeDetailComponent,
        EmployeeUpdateComponent,
        EmployeeDeleteDialogComponent,
        EmployeeDeletePopupComponent,
        FormUploadComponent
    ],
    entryComponents: [EmployeeComponent, EmployeeUpdateComponent, EmployeeDeleteDialogComponent, EmployeeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeatallocationEmployeeModule {}
