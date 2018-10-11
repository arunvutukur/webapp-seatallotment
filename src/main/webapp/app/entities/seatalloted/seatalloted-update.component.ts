import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISeatalloted } from 'app/shared/model/seatalloted.model';
import { SeatallotedService } from './seatalloted.service';

@Component({
    selector: 'jhi-seatalloted-update',
    templateUrl: './seatalloted-update.component.html'
})
export class SeatallotedUpdateComponent implements OnInit {
    private _seatalloted: ISeatalloted;
    isSaving: boolean;

    constructor(private seatallotedService: SeatallotedService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ seatalloted }) => {
            this.seatalloted = seatalloted;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.seatalloted.id !== undefined) {
            this.subscribeToSaveResponse(this.seatallotedService.update(this.seatalloted));
        } else {
            this.subscribeToSaveResponse(this.seatallotedService.create(this.seatalloted));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISeatalloted>>) {
        result.subscribe((res: HttpResponse<ISeatalloted>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get seatalloted() {
        return this._seatalloted;
    }

    set seatalloted(seatalloted: ISeatalloted) {
        this._seatalloted = seatalloted;
    }
}
