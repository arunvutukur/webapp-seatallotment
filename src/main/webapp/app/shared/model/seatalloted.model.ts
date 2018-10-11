export interface ISeatalloted {
    id?: string;
    seatNumber?: string;
    location?: string;
    floor?: string;
    vacancy?: string;
    requestForApproval?: string;
    pendingForApproval?: string;
}

export class Seatalloted implements ISeatalloted {
    constructor(
        public id?: string,
        public seatNumber?: string,
        public location?: string,
        public floor?: string,
        public vacancy?: string,
        public requestForApproval?: string,
        public pendingForApproval?: string
    ) {}
}
