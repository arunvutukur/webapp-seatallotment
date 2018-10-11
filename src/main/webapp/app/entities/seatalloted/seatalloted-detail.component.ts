import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISeatalloted } from 'app/shared/model/seatalloted.model';

@Component({
    selector: 'jhi-seatalloted-detail',
    templateUrl: './seatalloted-detail.component.html'
})
export class SeatallotedDetailComponent implements OnInit {
    seatalloted: ISeatalloted;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ seatalloted }) => {
            this.seatalloted = seatalloted;
        });
    }

    previousState() {
        window.history.back();
    }
}
