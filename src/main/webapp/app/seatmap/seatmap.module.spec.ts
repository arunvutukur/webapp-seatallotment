import { SeatmapModule } from './seatmap.module';

describe('SeatmapModule', () => {
    let seatmapModule: SeatmapModule;

    beforeEach(() => {
        seatmapModule = new SeatmapModule();
    });

    it('should create an instance', () => {
        expect(seatmapModule).toBeTruthy();
    });
});
