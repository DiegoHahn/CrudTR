
  import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Accountant } from './accountant';

@Injectable({
  providedIn: 'root'
})
export class PensamentoService {

  private readonly API = 'http://localhost:3000/accountants'

  constructor(private http: HttpClient) { }

  listar(pagina: number, filtro: string, favoritos: boolean): Observable<Accountant[]> {

    const itensPorPagina = 6;

    let params = new HttpParams()
      .set("_page", pagina)
      .set("_limit", itensPorPagina)


    if(filtro.trim().length > 2) {
      params = params.set('q', filtro)
    }

    if(favoritos) {
      params = params.set('favorito', true)
    }
    
    return this.http.get<Accountant[]>(this.API, { params})
  }

  criar(accountant: Accountant): Observable<Accountant> {
    return this.http.post<Accountant>(this.API, accountant)
  }

  editar(accountant: Accountant): Observable<Accountant> {
    const url = `${this.API}/${accountant.id}`
    return this.http.put<Accountant>(url, accountant )
  }

  excluir(id: number): Observable<Accountant> {
    const url = `${this.API}/${id}`
    return this.http.delete<Accountant>(url)
  }

  buscarPorId(id: number): Observable<Accountant> {
    const url = `${this.API}/${id}`
    return this.http.get<Accountant>(url)
  }

}
