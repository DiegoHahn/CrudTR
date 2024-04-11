import { Component, Input, OnInit } from '@angular/core';
import { Accountant } from '../accountant';

@Component({
  selector: 'app-accountant',
  templateUrl: './accountant.component.html',
  styleUrls: ['./accountant.component.css']
})
export class AccountantComponent implements OnInit {

  @Input() accountant: Accountant = {
    id: 0,
    name: 'modelo3',
    registrationNumber: '123445678912',
    accountantCode: '123',
    isActive: true
  }

  constructor() { }

  ngOnInit(): void {
  }

  mostar(){
    console.log('Hola');
    console.log(this.accountant);
  }
}
