import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProfileTypeRating } from 'app/shared/model/profile-type-rating.model';

type EntityResponseType = HttpResponse<IProfileTypeRating>;
type EntityArrayResponseType = HttpResponse<IProfileTypeRating[]>;

@Injectable({ providedIn: 'root' })
export class ProfileTypeRatingService {
  public resourceUrl = SERVER_API_URL + 'api/profile-type-ratings';

  constructor(protected http: HttpClient) {}

  create(profileTypeRating: IProfileTypeRating): Observable<EntityResponseType> {
    return this.http.post<IProfileTypeRating>(this.resourceUrl, profileTypeRating, { observe: 'response' });
  }

  update(profileTypeRating: IProfileTypeRating): Observable<EntityResponseType> {
    return this.http.put<IProfileTypeRating>(this.resourceUrl, profileTypeRating, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProfileTypeRating>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfileTypeRating[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
