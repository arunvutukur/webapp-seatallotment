/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SeatallocationTestModule } from '../../../test.module';
import { SeatallotedDetailComponent } from 'app/entities/seatalloted/seatalloted-detail.component';
import { Seatalloted } from 'app/shared/model/seatalloted.model';

describe('Component Tests', () => {
    describe('Seatalloted Management Detail Component', () => {
        let comp: SeatallotedDetailComponent;
        let fixture: ComponentFixture<SeatallotedDetailComponent>;
        const route = ({ data: of({ seatalloted: new Seatalloted('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SeatallocationTestModule],
                declarations: [SeatallotedDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SeatallotedDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SeatallotedDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.seatalloted).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
