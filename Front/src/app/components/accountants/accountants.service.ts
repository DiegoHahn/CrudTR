import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Accountant } from './accountant';

@Injectable({
  providedIn: 'root'
})
export class AccountantService {

  private readonly API = 'http://localhost:8080/accountants'
  
  constructor(private http: HttpClient) { }

  listAccountant(): Observable<Accountant[]> {
    return this.http.get<Accountant[]>(this.API)
  }

  create(accountant: Accountant):any {
      return this.http.post<Accountant>(this.API, accountant)
    }

  edit(accountant: Accountant): Observable<Accountant> {
    console.log(accountant)
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
