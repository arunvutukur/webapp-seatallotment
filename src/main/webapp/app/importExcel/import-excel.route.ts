import { Routes } from '@angular/router';
import { ImportExcelComponent } from './import-excel.component';

export const importExcelRoutes: Routes = [
    {
        path: 'import-excel',
        component: ImportExcelComponent,
        outlet: 'import'
    }
];
