import { NgModule } from '@angular/core';

import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AutosizeModule } from 'ngx-autosize';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { MyAdvertisementsComponent } from './components/myadvertisements/myadvertisements.component';

import { AuthService } from './services/auth.service';
import { TokenStorageService } from './services/token-storage.service';
import { AdvertisementService } from './services/advertisement.service';
import { authInterceptorProviders } from './helpers/auth.interceptor';
import { EditAdvertisementComponent } from './components/editadvertisement/editadvertisement.component';
import { CreateAdvertisementComponent } from './components/createadvertisement/createadvertisement.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    MyAdvertisementsComponent,
    EditAdvertisementComponent,
    CreateAdvertisementComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AutosizeModule
  ],
  providers: [
    AuthService,
    TokenStorageService,
    AdvertisementService,
    authInterceptorProviders
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
