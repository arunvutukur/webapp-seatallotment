import { NgModule } from '@angular/core';

import { SeatallocationSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [SeatallocationSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [SeatallocationSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SeatallocationSharedCommonModule {}
