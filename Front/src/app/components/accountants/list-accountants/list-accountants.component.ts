import { Component, OnInit } from '@angular/core';
import { AccountantService } from '../accountants.service';
import { Accountant } from './../accountant';


@Component({
  selector: 'app-list-accountants',
  templateUrl: './list-accountants.component.html',
  styleUrls: ['./list-accountants.component.css']
})
export class ListAccountantsComponent implements OnInit {

  currentPage: number = 1;
  nameFilter: string = '';
  listAccountants: Accountant[] = [];
  showDeleteConfirmation = false;
  selectedAccountId!: number;
    
  constructor(private service: AccountantService) { }

  ngOnInit(): void {
    this.loadAccountants();
  };

  loadAccountants(){
    this.service.listAccountant(this.nameFilter, this.currentPage).subscribe(data => {
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


