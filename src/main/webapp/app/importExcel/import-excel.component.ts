import { Component, OnInit } from '@angular/core';

import { HttpResponse, HttpEventType, HttpErrorResponse } from '@angular/common/http';
import { UploadFileService } from './upload-file.service';

@Component({
    // tslint:disable-next-line:component-selector
    selector: 'import-excel',
    templateUrl: './import-excel.component.html'
})
export class ImportExcelComponent implements OnInit {
    selectedFiles: FileList;
    currentFileUpload: File;
    success: boolean;
    fileNotExcel: string;
    sheetNotFound: string;
    validFile: boolean;
    internalServerError: string;
    missingSheet: string;
    progress: { percentage: number } = { percentage: 0 };

    constructor(private uploadService: UploadFileService) {}

    ngOnInit() {}

    selectFile(event) {
        this.selectedFiles = event.target.files;
    }

    upload() {
        this.progress.percentage = 0;

        this.currentFileUpload = this.selectedFiles.item(0);
        this.uploadService.pushFileToStorage(this.currentFileUpload).subscribe(
            event => {
                if (event.type === HttpEventType.UploadProgress) {
                    this.validFile = true;
                    this.progress.percentage = Math.round((100 * event.loaded) / event.total);
                } else if (event instanceof HttpResponse) {
                    console.log('File is completely uploaded!');
                    this.success = true;
                    this.currentFileUpload = undefined;
                }
            },
            responseEntity => this.processError(responseEntity)
        );

        this.selectedFiles = undefined;
    }

    private processError(responseEntity: HttpErrorResponse) {
        this.success = false;
        this.validFile = false;
        // 406: status code for NOT_ACCEPTABLE
        if (responseEntity.status === 406 && responseEntity.statusText === 'Not Acceptable') {
            this.fileNotExcel = 'ERROR';
            console.log('in process error');
        } else if (responseEntity.status === 417 && responseEntity.statusText === 'Expectation Failed') {
            this.sheetNotFound = 'ERROR';
        } else if (responseEntity.status === 500 && responseEntity.statusText === 'Internal Server Error') {
            this.internalServerError = 'ERROR';
        } else if (responseEntity.status === 204 && responseEntity.statusText === 'No Content') {
            this.missingSheet = 'ERROR';
        }
    }
}
