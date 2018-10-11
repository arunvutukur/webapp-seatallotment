import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISeatalloted } from 'app/shared/model/seatalloted.model';
import { SeatallotedService } from './seatalloted.service';

@Component({
    selector: 'jhi-seatalloted-delete-dialog',
    templateUrl: './seatalloted-delete-dialog.component.html'
})
export class SeatallotedDeleteDialogComponent {
    seatalloted: ISeatalloted;

    constructor(
        private seatallotedService: SeatallotedService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.seatallotedService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'seatallotedListModification',
                content: 'Deleted an seatalloted'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-seatalloted-delete-popup',
    template: ''
})
export class SeatallotedDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ seatalloted }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SeatallotedDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.seatalloted = seatalloted;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
