export interface ISeatalloted {
    id?: string;
    seatNumber?: string;
    location?: string;
    floor?: string;
    alloted?: boolean;
    requestForApproval?: boolean;
    pendingForApproval?: boolean;
}

export class Seatalloted implements ISeatalloted {
    constructor(
        public id?: string,
        public seatNumber?: string,
        public location?: string,
        public floor?: string,
        public alloted?: boolean,
        public requestForApproval?: boolean,
        public pendingForApproval?: boolean
    ) {
        this.alloted = this.alloted || false;
        this.requestForApproval = this.requestForApproval || false;
        this.pendingForApproval = this.pendingForApproval || false;
    }
}
