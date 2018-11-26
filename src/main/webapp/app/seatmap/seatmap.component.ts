import { Component, OnInit } from '@angular/core';
import { EmployeeService } from 'app/entities/employee';
import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { loadElementInternal } from '@angular/core/src/render3/util';

@Component({
    selector: 'jhi-seatmap',
    templateUrl: './seatmap.component.html',
    styles: []
})
export class SeatmapComponent implements OnInit {
    employees: IEmployee[];
    managers: string[];
    constructor(private employeeService: EmployeeService) {}
    ngOnInit() {
        this.loadAll();
    }

    loadAll(): void {
        this.employeeService.getAllEmployees().subscribe(data => {
            this.employees = data;
        });
        this.managers = Array.from(new Set(this.employees.map(employee => employee.manager)));
        // this.managers = Array.from(new Set(this.employees.map(employee => employee.manager)));
    }
}
