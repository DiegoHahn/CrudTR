import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-delete-accountant',
  templateUrl: './delete-accountant.component.html',
  styleUrls: ['./delete-accountant.component.css']
})
export class DeleteAccountantComponent {

  @Input() errorMessage: string;
  
  //emite um evento para o componente pai para confirmar a exclusão
  @Output() confirmation = new EventEmitter<boolean>();
  constructor() { }

  confirmDeletion() {
    this.confirmation.emit(true);
  }

  cancelDeletion() {
    this.confirmation.emit(false);
  }
}
  
