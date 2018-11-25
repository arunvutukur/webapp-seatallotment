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
    public resourceUrl = SERVER_API_URL + 'api/seatalloteds';

    constructor(private http: HttpClient) {}

    create(seatalloted: ISeatalloted): Observable<EntityResponseType> {
        return this.http.post<ISeatalloted>(this.resourceUrl, seatalloted, { observe: 'response' });
    }

    update(seatalloted: ISeatalloted): Observable<EntityResponseType> {
        return this.http.put<ISeatalloted>(this.resourceUrl, seatalloted, { observe: 'response' });
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
}
