import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
@Injectable({
    providedIn: 'root'
})
export class UploadFileService {
    private resourceUrl = SERVER_API_URL + 'api/post';
    constructor(private http: HttpClient) {}
    pushFileToStorage(file: File): Observable<HttpEvent<{}>> {
        const formdata: FormData = new FormData();

        formdata.append('file', file);

        const req = new HttpRequest<FormData>('POST', this.resourceUrl, formdata, {
            reportProgress: true,
            responseType: 'text'
        });
        return this.http.request(req);
    }
}
