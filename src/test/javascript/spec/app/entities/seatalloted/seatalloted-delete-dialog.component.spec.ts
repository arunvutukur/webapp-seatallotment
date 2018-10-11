/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SeatallocationTestModule } from '../../../test.module';
import { SeatallotedDeleteDialogComponent } from 'app/entities/seatalloted/seatalloted-delete-dialog.component';
import { SeatallotedService } from 'app/entities/seatalloted/seatalloted.service';

describe('Component Tests', () => {
    describe('Seatalloted Management Delete Component', () => {
        let comp: SeatallotedDeleteDialogComponent;
        let fixture: ComponentFixture<SeatallotedDeleteDialogComponent>;
        let service: SeatallotedService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SeatallocationTestModule],
                declarations: [SeatallotedDeleteDialogComponent]
            })
                .overrideTemplate(SeatallotedDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SeatallotedDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeatallotedService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete('123');
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith('123');
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
