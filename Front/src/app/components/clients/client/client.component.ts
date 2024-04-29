import { Component, ContentChild, Input, OnInit, TemplateRef } from '@angular/core';
import { Client } from '../client';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {

  @Input() listClients: Client[] = [];

  @ContentChild('actions', {static: false}) actionTemplateRef: TemplateRef <Client>;
  
  constructor() { }

  ngOnInit(): void {
  }
}
