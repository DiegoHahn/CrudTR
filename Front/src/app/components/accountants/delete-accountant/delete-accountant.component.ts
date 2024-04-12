import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-delete-accountant',
  templateUrl: './delete-accountant.component.html',
  styleUrls: ['./delete-accountant.component.css']
})
export class DeleteAccountantComponent {

  @Output() confirmation = new EventEmitter<boolean>();
  constructor() { }

  confirmDeletion() {
    this.confirmation.emit(true);
  }

  cancelDeletion() {
    this.confirmation.emit(false);
  }
}
  
