import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  isLoggedIn = false;

  items: MenuItem[];

  commonItems: MenuItem[] = [
    {
      label: 'Home',
      icon: 'pi pi-home',
      routerLink: ['']
    },
    {
      label: 'Service logs',
      icon: 'pi pi-list',
      routerLink: ['service-log/search']
    },
    {
      label: 'Service-log alarms',
      icon: 'pi pi-bell',
      routerLink: ['service-log-alarm']
    },
    {
      label: 'Patient messages',
      icon: 'pi pi-chart-bar',
      routerLink: ['medical-record-log']
    },
    {
      label: 'Patient messages alarms',
      icon: 'pi pi-bell',
      routerLink: ['medical-record-alarms']
    },
    {
      label: 'Create rule',
      icon: 'pi pi-exclamation-triangle',
      routerLink: ['rule/add-rule']
    }
  ];

  adminItems: MenuItem[] = [
    {
      label: 'Send Certificate Request',
      icon: 'pi pi-map',
     routerLink: ['']
    }
  ];
 
  constructor(private keycloakService: KeycloakService) { }

  async ngOnInit() {
    this.isLoggedIn = await this.keycloakService.isLoggedIn();
    if (this.isLoggedIn) {
      const roles: string[] = this.keycloakService.getUserRoles();
      if (roles.includes('admin'))
        this.items = [
          ...this.commonItems,
          ...this.adminItems
        ];
    }
    else {
      this.items = this.commonItems;
    }
  }

  login(): void {
    this.keycloakService.login();
  }

  logout(): void {
    this.keycloakService.logout();
  }

}
