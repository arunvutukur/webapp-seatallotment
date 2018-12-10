export interface IAccess {
    id?: string;
    associateId?: string;
    role?: string;
    password?: string;
}

export class Access implements IAccess {
    constructor(public id?: string, public associateId?: string, public role?: string, public password?: string) {}
}
