import { Component, OnInit } from '@angular/core';
import { AccountantService } from '../accountants.service';
import { Router } from '@angular/router';
import { Accountant } from '../accountant';

@Component({
  selector: 'app-list-accountants',
  templateUrl: './list-accountants.component.html',
  styleUrls: ['./list-accountants.component.css']
})
export class ListAccountantsComponent implements OnInit {
  listAccountants: Accountant[] = [];
    
  constructor(
  private service: AccountantService,
  private router: Router) { }

  ngOnInit(): void {
    this.loadAccountants();
  };

  loadAccountants(){
    this.service.listAccountant().subscribe(data => {
      this.listAccountants = data;
      console.log(this.listAccountants);
    });
  }
  
  mostar(){
    console.log('Hola');

  }

}
