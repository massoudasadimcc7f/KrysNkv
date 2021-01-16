import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProfileTypeInfo } from 'app/shared/model/profile-type-info.model';

type EntityResponseType = HttpResponse<IProfileTypeInfo>;
type EntityArrayResponseType = HttpResponse<IProfileTypeInfo[]>;

@Injectable({ providedIn: 'root' })
export class ProfileTypeInfoService {
  public resourceUrl = SERVER_API_URL + 'api/profile-type-infos';

  constructor(protected http: HttpClient) {}

  create(profileTypeInfo: IProfileTypeInfo): Observable<EntityResponseType> {
    return this.http.post<IProfileTypeInfo>(this.resourceUrl, profileTypeInfo, { observe: 'response' });
  }

  update(profileTypeInfo: IProfileTypeInfo): Observable<EntityResponseType> {
    return this.http.put<IProfileTypeInfo>(this.resourceUrl, profileTypeInfo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProfileTypeInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfileTypeInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
