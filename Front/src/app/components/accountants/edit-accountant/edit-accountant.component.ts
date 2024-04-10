import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AccountantService } from '../accountants.service';


@Component({
  selector: 'app-edit-accountant',
  templateUrl: './edit-accountant.component.html',
  styleUrls: ['./edit-accountant.component.css']
})
export class EditAccountantComponent implements OnInit {

  constructor(  
    private service: AccountantService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    console.log(id);

  }

}
