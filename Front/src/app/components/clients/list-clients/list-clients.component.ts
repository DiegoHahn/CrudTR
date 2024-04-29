import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { Subject, debounceTime } from 'rxjs';
import { Client } from '../client';
import { ClientService } from '../clients.service';

@Component({
  selector: 'app-list-clients',
  templateUrl: './list-clients.component.html',
  styleUrls: ['./list-clients.component.css']
})
export class ListClientsComponent implements OnInit {

  // @ViewChild(MatPaginator) paginator: MatPaginator;
  // pageIndex: number = 0;
  // pageSize: number = 10;
  // totalElements: number;
  // nameFilter: string = '';
  listClients: Client[] = [];
  //showDeleteConfirmation = false;
  // selectedAccountId!: number;
  // onKeyDown = new Subject<KeyboardEvent>();  


  constructor(private service: ClientService) { }

  ngOnInit(): void {
    this.loadClients();
  };

  loadClients(){
    this.service.listClientsData().subscribe((response: Client[]) => {
      this.listClients  = response;
    }); 
  }

//   renderDeleteConfirmation(accountantID: number) {
//     this.selectedAccountId = accountantID
//     this.showDeleteConfirmation = true;
//   }

//   filterByName() {
//     this.pageIndex = 0;
//     this.loadAccountants(this.pageIndex, this.pageSize);
    
//     this.paginator.pageIndex = 0; 

//   };
  
//   //passa o evento de paginação para o metodo loadAccountants com o pageIndex e pageSize atualizados
//   changePage($event: PageEvent) {
//     this.loadAccountants($event.pageIndex, $event.pageSize);
//   }

//   //operações assíncronas são tratadas como observables para isso o metodo subscribe tem os metodos next, error e complete
//   onDeleteConfirm(confirmation: boolean) {
//     if(confirmation) {
//      this.service.delete(this.selectedAccountId).subscribe({
//        next: () => {
//          //quando o delete é feito com sucesso, recarrega a lista de contadores
//          this.loadAccountants(this.pageIndex, this.pageSize);
//        },
//        error: (error) => {
//          //tratamento de erro
//          console.log(error);
//        },
//        complete: () => {
//          //quando o delete é feito com sucesso, fecha o modal de confirmação
//          this.showDeleteConfirmation = false;
//        },
//      });
//    }
//    //se o usuário clicar em cancelar emite o confirmation false e fecha o modal de confirmação
//    else {
//      this.showDeleteConfirmation = false;
//    }
//  }
}


