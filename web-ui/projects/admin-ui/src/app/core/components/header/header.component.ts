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
    // {
    //   label: 'Issue Certificate',
    //   icon: 'pi pi-map',
    //   routerLink: ['/certificates/issue-certificate']
    // },
  ];

  superAdminItems: MenuItem[] = [
    {
      label: 'List Certificates',
      icon: 'pi pi-map',
     routerLink: ['/certificates']
    },
    {
      label: 'Certificate Requests',
      icon: 'pi pi-map',
     routerLink: ['/certificates/requests']
    },
    {
      label: 'Issue Certificate',
      icon: 'pi pi-map',
     routerLink: ['/certificates/issue']
    }
  ];

  constructor(private keycloakService: KeycloakService) { }

  async ngOnInit() {
    this.isLoggedIn = await this.keycloakService.isLoggedIn();
    if (this.isLoggedIn) {
      const roles: string[] = this.keycloakService.getUserRoles();
      if (roles.includes('super-admin'))
        this.items = [
          ...this.commonItems,
          ...this.superAdminItems
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
