import { Component, OnInit } from '@angular/core';
import { AccountantService } from '../accountants.service';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-list-accountants',
  templateUrl: './list-accountants.component.html',
  styleUrls: ['./list-accountants.component.css']
})
export class ListAccountantsComponent implements OnInit {
  accountants: any;

  constructor(
  private service: AccountantService,
  private router: Router,
  private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.loadAccountants();
  };

  loadAccountants(){
    this.service.listAccountant().subscribe(data => {
      this.accountants = data;
      this.accountants.forEach(console.log);
    });
  }
  
}
