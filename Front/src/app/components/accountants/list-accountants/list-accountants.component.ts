import { Component, OnInit } from '@angular/core';
import { AccountantService } from '../accountants.service';
import { Accountant } from './../accountant';
import { Subject, debounceTime } from 'rxjs';


@Component({
  selector: 'app-list-accountants',
  templateUrl: './list-accountants.component.html',
  styleUrls: ['./list-accountants.component.css']
})
export class ListAccountantsComponent implements OnInit {

  totalItems: number;
  nameFilter: string = '';
  listAccountants: Accountant[] = [];
  showDeleteConfirmation = false;
  selectedAccountId!: number;
  onKeyDown = new Subject<KeyboardEvent>();  

  constructor(private service: AccountantService) { }

  ngOnInit(): void {
    this.loadAccountants()
    this.service.getTotalRecordsNumber().subscribe((totalItems: number) => {
      this.totalItems = totalItems;
    });
    this.onKeyDown.pipe(debounceTime(2000)).subscribe(_ => {
      this.filterByName();
    });
  };

  loadAccountants(){
    this.service.listAccountant(this.nameFilter).subscribe(data => {
      this.listAccountants = data;
    });
  }

  renderDeleteConfirmation(accountantID: number) {
    this.selectedAccountId = accountantID
    this.showDeleteConfirmation = true;
  }

  //operações assíncronas são tratadas como observables para isso o metodo subscribe tem os metodos next, error e complete
  onDeleteConfirm(confirmation: boolean) {
     if(confirmation) {
      this.service.delete(this.selectedAccountId).subscribe({
        next: () => {
          //quando o delete é feito com sucesso, recarrega a lista de contadores
          this.loadAccountants();
          this.service.getTotalRecordsNumber().subscribe((total: number) => {
            this.totalItems = total;
          });
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

  filterByName() {
   this.loadAccountants();
  };
}


