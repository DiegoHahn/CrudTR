import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Client } from './client';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private readonly API = 'http://localhost:8080/clients'

  constructor(private http: HttpClient) { }

  listClientsData(): Observable<Client[]> {
    // let params = new HttpParams()
    // params = params
    //   .set('name', nameFilter)
    //   .set('page', pageIndex)
    //   .set('size', pageSize);
    return this.http.get<Client[]>(this.API)
      .pipe(catchError(error => {
        return throwError(() => new Error(error));
      }));
  }

//   create(client: Client):any {
//       return this.http.post<Client>(this.API, client)
//     }

//   edit(client: Client): Observable<Client> {
//     const url = `${this.API}/${client.id}`
//     return this.http.put<Client>(url, client )
//   }

//   delete(id: number): Observable<Client> {
//     const url = `${this.API}/${id}`
//     return this.http.delete<Client>(url)
//   }

//   searchAccountByID(id: number): Observable<Client> {
//     const url = `${this.API}/${id}`
//     return this.http.get<Client>(url)
//   }
}
