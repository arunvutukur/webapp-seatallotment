import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UploadFileService } from './upload-file.service';
import { RouterModule } from '@angular/router';
import { importExcelRoutes } from './import-excel.route';
import { ImportExcelComponent } from './import-excel.component';

@NgModule({
    imports: [CommonModule, RouterModule.forChild(importExcelRoutes)],
    declarations: [ImportExcelComponent],
    providers: [UploadFileService],
    exports: [RouterModule],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ImportExcelModule {}
