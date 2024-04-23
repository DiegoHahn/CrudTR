import { AccountantResponse } from './../accountant-response';
import { Component, OnInit, ViewChild } from '@angular/core';
import { AccountantService } from '../accountants.service';
import { Accountant } from './../accountant';
import { Subject, debounceTime, map } from 'rxjs';
import { MatPaginator, PageEvent } from '@angular/material/paginator';


@Component({
  selector: 'app-list-accountants',
  templateUrl: './list-accountants.component.html',
  styleUrls: ['./list-accountants.component.css']
})
export class ListAccountantsComponent implements OnInit {

  //ViewChild para manipular o paginator
  @ViewChild(MatPaginator) paginator: MatPaginator;
  pageIndex: number = 0;
  pageSize: number = 10;
  totalElements: number;
  nameFilter: string = '';
  listAccountants: Accountant[] = [];
  showDeleteConfirmation = false;
  selectedAccountId!: number;
  onKeyDown = new Subject<KeyboardEvent>();  

  constructor(private service: AccountantService) { }

  ngOnInit(): void {
    this.loadAccountants(this.pageIndex, this.pageSize);
    this.onKeyDown.pipe(debounceTime(800)).subscribe(_ => {
      this.filterByName();
    });
  };

  loadAccountants(pageIndex: number, pageSize: number){
    this.service.listAccountantsData(this.nameFilter, pageIndex, pageSize).subscribe((response: AccountantResponse) => {
      this.listAccountants = response.content;
      this.totalElements = response.totalElements;
    });
  }

  renderDeleteConfirmation(accountantID: number) {
    this.selectedAccountId = accountantID
    this.showDeleteConfirmation = true;
  }

  filterByName() {
    this.pageIndex = 0;
    this.loadAccountants(this.pageIndex, this.pageSize);
    //precisei utilizar o paginator pois quando eu fazia uma pesquisa na pagina 2 o paginator não retornava para o indice 0
    this.paginator.pageIndex = 0; 

  };
  
  //passa o evento de paginação para o metodo loadAccountants com o pageIndex e pageSize atualizados
  changePage($event: PageEvent) {
    this.loadAccountants($event.pageIndex, $event.pageSize);
  }

  //operações assíncronas são tratadas como observables para isso o metodo subscribe tem os metodos next, error e complete
  onDeleteConfirm(confirmation: boolean) {
    if(confirmation) {
     this.service.delete(this.selectedAccountId).subscribe({
       next: () => {
         //quando o delete é feito com sucesso, recarrega a lista de contadores
         this.loadAccountants(this.pageIndex, this.pageSize);
       },
       error: (error) => {
         //tratamento de erro
         console.log(error);
       },
       complete: () => {
         //quando o delete é feito com sucesso, fecha o modal de confirmação
         this.showDeleteConfirmation = false;
       },
     });
   }
   //se o usuário clicar em cancelar emite o confirmation false e fecha o modal de confirmação
   else {
     this.showDeleteConfirmation = false;
   }
 }
}


