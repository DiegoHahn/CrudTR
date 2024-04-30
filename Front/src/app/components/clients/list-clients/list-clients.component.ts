import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { Subject, debounceTime } from 'rxjs';
import { ClientResponse } from '../client.response';
import { ClientService } from '../clients.service';
import { Client } from './../client';

@Component({
  selector: 'app-list-clients',
  templateUrl: './list-clients.component.html',
  styleUrls: ['./list-clients.component.css']
})
export class ListClientsComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  pageIndex: number = 0;
  pageSize: number = 10;
  totalElements: number;
  nameFilter: string = '';
  listClients: Client[] = [];
  showDeleteConfirmation = false;
  selectedClientId!: number;
  onKeyDown = new Subject<KeyboardEvent>();  


  constructor(private service: ClientService) { }

  ngOnInit(): void {
    this.loadClients(this.pageIndex, this.pageSize);
    this.onKeyDown.pipe(debounceTime(800)).subscribe(_ => {
      this.filterByName();
    });
  };

  loadClients(pageIndex: number, pageSize: number){
    this.service.listClientsData(this.nameFilter, pageIndex, pageSize).subscribe((response: ClientResponse) => {
      this.listClients = response.content;
      this.totalElements = response.totalElements;
    }); 
  }

  renderDeleteConfirmation(clientID: number) {
    this.selectedClientId = clientID
    this.showDeleteConfirmation = true;
  }

  filterByName() {
    this.pageIndex = 0;
    this.loadClients(this.pageIndex, this.pageSize);
    this.paginator.pageIndex = 0; 
  };
  
  changePage($event: PageEvent) {
    this.loadClients($event.pageIndex, $event.pageSize);
  }

  onDeleteConfirm(confirmation: boolean) {
    if(confirmation) {
    console.log("aqui +",this.selectedClientId)
     this.service.delete(this.selectedClientId).subscribe({
       next: () => {
         this.loadClients(this.pageIndex, this.pageSize);
       },
       error: (error) => {
         console.log(error);
       },
       complete: () => {
         this.showDeleteConfirmation = false;
       },
     });
   }
   else {
     this.showDeleteConfirmation = false;
   }
 }
}


