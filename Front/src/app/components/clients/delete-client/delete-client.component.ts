import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-delete-client',
  templateUrl: './delete-client.component.html',
  styleUrls: ['./delete-client.component.css']
})
export class DeleteClientComponent{

  @Output() confirmation = new EventEmitter<boolean>();
  constructor() { }

  confirmDeletion() {
    this.confirmation.emit(true);
  }

  cancelDeletion() {
    this.confirmation.emit(false);
  }
}
