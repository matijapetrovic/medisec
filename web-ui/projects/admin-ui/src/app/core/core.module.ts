import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { RouterModule } from '@angular/router';

import {Menubar, MenubarModule} from 'primeng/menubar';
import { KeycloakBearerInterceptor, KeycloakService } from 'keycloak-angular';
import { ButtonModule } from 'primeng/button';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { VendorsModule } from 'projects/vendors/src/public-api';


@NgModule({
  declarations: [HeaderComponent],
  imports: [
    CommonModule,
    RouterModule,
    VendorsModule
  ],
  exports: [HeaderComponent],
  providers: [KeycloakService,
      {
        provide: HTTP_INTERCEPTORS,
        useClass: KeycloakBearerInterceptor,
        multi: true
      }
    ]
})
export class CoreModule { }
