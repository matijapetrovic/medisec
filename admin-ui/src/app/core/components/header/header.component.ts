import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  items: MenuItem[] = [
    {
      label: 'Home',
      icon: 'pi pi-home',
      routerLink: ['']
    },
    {
      label: 'Issue Certificate',
      icon: 'pi pi-map',
      routerLink: ['/certificates/issue-certificate']
    },
    {
      label: 'List Certificates',
      icon: 'pi pi-map',
     routerLink: ['/certificates']
    },
    {
      label: 'Certificate Requests',
      icon: 'pi pi-map',
     routerLink: ['/certificates/requests']
    }
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
