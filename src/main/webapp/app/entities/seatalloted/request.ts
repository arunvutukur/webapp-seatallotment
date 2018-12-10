import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISeatalloted, Seatalloted } from 'app/shared/model/seatalloted.model';
import { SeatallotedService } from './seatalloted.service';

@Component({
    selector: 'jhi-seatalloted-request',
    templateUrl: './seatalloted.component.html'
})
export class RequestComponent implements OnInit {
    private seatalloted: ISeatalloted;
    isSaving: boolean;

    constructor(private seatallotedService: SeatallotedService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ seatalloted }) => {
            this.seatalloted = seatalloted;
        });
        // this.updateRequested(true);
    }

    previousState() {
        window.history.back();
    }

    updateRequested(requestForApproval: boolean) {
        this.seatallotedService.updateRequestForApproval(this.seatalloted, requestForApproval).subscribe(data => {
            this.seatalloted = data as Seatalloted;
        });
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISeatalloted>>) {
        result.subscribe((res: HttpResponse<ISeatalloted>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = true;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
