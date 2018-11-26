import { ImportModule } from './import.module';

describe('ImportModule', () => {
    let importModule: ImportModule;

    beforeEach(() => {
        importModule = new ImportModule();
    });

    it('should create an instance', () => {
        expect(importModule).toBeTruthy();
    });
});
