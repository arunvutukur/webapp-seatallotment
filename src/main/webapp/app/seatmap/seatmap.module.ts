import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SeatmapComponent } from './seatmap.component';
import { RouterModule } from '@angular/router';
import { seatmapRoutes } from './seatmap.route';
import { EmployeeService } from 'app/entities/employee';

@NgModule({
    imports: [CommonModule, RouterModule.forChild(seatmapRoutes)],
    declarations: [SeatmapComponent],
    providers: [EmployeeService]
})
export class SeatmapModule {}
