import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAccess } from 'app/shared/model/access.model';
import { AccessService } from './access.service';

@Component({
    selector: 'jhi-access-update',
    templateUrl: './access-update.component.html'
})
export class AccessUpdateComponent implements OnInit {
    private _access: IAccess;
    isSaving: boolean;

    constructor(private accessService: AccessService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ access }) => {
            this.access = access;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.access.id !== undefined) {
            this.subscribeToSaveResponse(this.accessService.update(this.access));
        } else {
            this.subscribeToSaveResponse(this.accessService.create(this.access));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAccess>>) {
        result.subscribe((res: HttpResponse<IAccess>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get access() {
        return this._access;
    }

    set access(access: IAccess) {
        this._access = access;
    }
}
