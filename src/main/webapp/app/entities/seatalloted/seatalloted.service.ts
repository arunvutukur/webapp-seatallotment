import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISeatalloted } from 'app/shared/model/seatalloted.model';

type EntityResponseType = HttpResponse<ISeatalloted>;
type EntityArrayResponseType = HttpResponse<ISeatalloted[]>;

@Injectable({ providedIn: 'root' })
export class SeatallotedService {
    private resourceUrl = SERVER_API_URL + 'api/seatalloteds';
    public NUMBER_OF_REQUESTS: number;
    constructor(private http: HttpClient) {
        this.NUMBER_OF_REQUESTS = 0;
    }

    create(seatalloted: ISeatalloted): Observable<EntityResponseType> {
        return this.http.post<ISeatalloted>(this.resourceUrl, seatalloted, { observe: 'response' });
    }

    update(seatalloted: ISeatalloted): Observable<EntityResponseType> {
        return this.http.put<ISeatalloted>(this.resourceUrl, seatalloted, { observe: 'response' });
    }

    updateRequestForApproval(seatalloted: ISeatalloted, requestForApproval: boolean): Observable<any> {
        return this.http.put<ISeatalloted>(`${this.resourceUrl}` + `/request`, seatalloted);
    }
    find(id: string): Observable<EntityResponseType> {
        return this.http.get<ISeatalloted>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISeatalloted[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
    setNumberOfRequests(NUMBER_OF_REQUESTS) {
        this.NUMBER_OF_REQUESTS = NUMBER_OF_REQUESTS;
    }

    getNumberOfRequests() {
        return this.NUMBER_OF_REQUESTS;
    }
}
