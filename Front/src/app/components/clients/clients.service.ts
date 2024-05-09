import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Client } from './client';
import { ClientResponse } from './client.response';
import { Accountant } from '../accountants/accountant';
import { AccountantResponse } from '../accountants/accountant-response';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  
  private readonly clientAPI = 'http://localhost:8080/clients'
  private readonly accountantAPI = 'http://localhost:8080/accountants'


  constructor(private http: HttpClient) { }

  listClientsData(nameFilter:string, pageIndex:number, pageSize:number): Observable<ClientResponse> {
    let params = new HttpParams()
    params = params
      .set('name', nameFilter)
      .set('page', pageIndex)
      .set('size', pageSize);
    return this.http.get<ClientResponse>(this.clientAPI, {params})
      .pipe(catchError(error => {
        return throwError(() => new Error(error));
      }));
  }
  
  listAccountantsData(nameFilter:string, pageIndex:number, pageSize:number): Observable<AccountantResponse> {
    let params = new HttpParams()
    params = params
      .set('name', nameFilter)
      .set('page', pageIndex)
      .set('size', pageSize);
    return this.http.get<AccountantResponse>(this.accountantAPI, {params})
  }

  getAccountants(): Observable<Accountant[]> {
    const url = `${this.accountantAPI}/all`
     return this.http.get<Accountant[]>(url);
  }

  create(client: Client):any {
      return this.http.post<Client>(this.clientAPI, client)
    }

  edit(client: Client): Observable<Client> {
    const url = `${this.clientAPI}/${client.id}`
    return this.http.put<Client>(url, client )
  }

  delete(id: number): Observable<Client> {
    const url = `${this.clientAPI}/${id}`
    return this.http.delete<Client>(url)
  }

  searchClientByID(id: number): Observable<Client> {
    const url = `${this.clientAPI}/${id}`
    return this.http.get<Client>(url)
  }
}
