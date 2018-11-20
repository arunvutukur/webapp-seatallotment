export interface IEmployee {
    id?: string;
    name?: string;
    empId?: string;
    manager?: string;
    email?: string;
    location?: string;
    department?: string;
    requestForApproval?: boolean;
    pendingForApproval?: boolean;
}

export class Employee implements IEmployee {
    constructor(
        public id?: string,
        public name?: string,
        public empId?: string,
        public manager?: string,
        public email?: string,
        public location?: string,
        public department?: string,
        public requestForApproval?: boolean,
        public pendingForApproval?: boolean
    ) {
        this.requestForApproval = this.requestForApproval || false;
        this.pendingForApproval = this.pendingForApproval || false;
    }
}
