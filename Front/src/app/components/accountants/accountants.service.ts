import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Accountant } from './accountant';
import { AccountantResponse } from './accountant-response';

@Injectable({
  providedIn: 'root'
})
export class AccountantService {

  private readonly API = 'http://localhost:8080/accountants'
  
  constructor(private http: HttpClient) { }

  listAccountantsData(nameFilter:string, pageIndex:number, pageSize:number): Observable<AccountantResponse> {
    let params = new HttpParams()
    params = params
      .set('name', nameFilter)
      .set('page', pageIndex)
      .set('size', pageSize);
    return this.http.get<AccountantResponse>(this.API, {params})
      .pipe(catchError(error => {
        return throwError(() => new Error(error));
      }));
  }

  create(accountant: Accountant):any {
      return this.http.post<Accountant>(this.API, accountant)
    }

  edit(accountant: Accountant): Observable<Accountant> {
    const url = `${this.API}/${accountant.id}`
    return this.http.put<Accountant>(url, accountant )
  }

  delete(id: number): Observable<Accountant> {
    const url = `${this.API}/${id}`
    return this.http.delete<Accountant>(url)
  }

  searchAccountByID(id: number): Observable<Accountant> {
    const url = `${this.API}/${id}`
    return this.http.get<Accountant>(url)
  }
}
