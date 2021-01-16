import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProfileType } from 'app/shared/model/profile-type.model';

type EntityResponseType = HttpResponse<IProfileType>;
type EntityArrayResponseType = HttpResponse<IProfileType[]>;

@Injectable({ providedIn: 'root' })
export class ProfileTypeService {
  public resourceUrl = SERVER_API_URL + 'api/profile-types';

  constructor(protected http: HttpClient) {}

  create(profileType: IProfileType): Observable<EntityResponseType> {
    return this.http.post<IProfileType>(this.resourceUrl, profileType, { observe: 'response' });
  }

  update(profileType: IProfileType): Observable<EntityResponseType> {
    return this.http.put<IProfileType>(this.resourceUrl, profileType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProfileType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfileType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
