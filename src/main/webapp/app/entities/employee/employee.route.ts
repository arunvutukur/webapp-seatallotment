import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Employee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { EmployeeComponent } from './employee.component';
import { EmployeeDetailComponent } from './employee-detail.component';
import { EmployeeUpdateComponent } from './employee-update.component';
import { EmployeeDeletePopupComponent } from './employee-delete-dialog.component';
import { IEmployee } from 'app/shared/model/employee.model';
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class EmployeeResolve implements Resolve<IEmployee> {
    constructor(private service: EmployeeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['empId'] ? route.params['empId'] : null;
        if (id) {
            return this.service.find(id).pipe(map((employee: HttpResponse<Employee>) => employee.body));
        }
        return Observable.of(new Employee());
    }
}

export const employeeRoute: Routes = [
    {
        path: 'employee',
        component: EmployeeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Employees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee/:empId/view',
        component: EmployeeDetailComponent,
        resolve: {
            employee: EmployeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee/new',
        component: EmployeeUpdateComponent,
        resolve: {
            employee: EmployeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee/:empId/edit',
        component: EmployeeUpdateComponent,
        resolve: {
            employee: EmployeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employees'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeePopupRoute: Routes = [
    {
        path: 'employee/:empId/delete',
        component: EmployeeDeletePopupComponent,
        resolve: {
            employee: EmployeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employees'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
