/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SeatallocationTestModule } from '../../../test.module';
import { SeatallotedUpdateComponent } from 'app/entities/seatalloted/seatalloted-update.component';
import { SeatallotedService } from 'app/entities/seatalloted/seatalloted.service';
import { Seatalloted } from 'app/shared/model/seatalloted.model';

describe('Component Tests', () => {
    describe('Seatalloted Management Update Component', () => {
        let comp: SeatallotedUpdateComponent;
        let fixture: ComponentFixture<SeatallotedUpdateComponent>;
        let service: SeatallotedService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SeatallocationTestModule],
                declarations: [SeatallotedUpdateComponent]
            })
                .overrideTemplate(SeatallotedUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SeatallotedUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeatallotedService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Seatalloted('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.seatalloted = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Seatalloted();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.seatalloted = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
