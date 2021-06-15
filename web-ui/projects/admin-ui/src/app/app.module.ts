import { BrowserModule } from '@angular/platform-browser';
import { APP_INITIALIZER, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';

import {HttpClientModule} from '@angular/common/http';

import { ToastModule } from 'primeng/toast';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { KeycloakService } from 'keycloak-angular';
import { initializer } from './keycloak/init';
import { MessageService } from 'primeng/api';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    CoreModule,
    ToastModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializer,
      multi: true,
      deps: [KeycloakService]
    },
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
