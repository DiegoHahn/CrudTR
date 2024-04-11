import { Component, ContentChild, Input, OnInit, TemplateRef } from '@angular/core';
import { Accountant } from '../accountant';

@Component({
  selector: 'app-accountant',
  templateUrl: './accountant.component.html',
  styleUrls: ['./accountant.component.css']
})
export class AccountantComponent implements OnInit {

  @Input() listAccountants: Accountant[] = [];

  @ContentChild('actions', {static: false}) actionTemplateRef: TemplateRef <any>;
  
  constructor() { }

  ngOnInit(): void {
  }

  mostar(){
    console.log('Hola');
  }
}
