import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyAdvertisementsComponent } from './components/myadvertisements/myadvertisements.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { EditAdvertisementComponent } from './components/editadvertisement/editadvertisement.component';
import { CreateAdvertisementComponent } from './components/createadvertisement/createadvertisement.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'myadvertisements', component: MyAdvertisementsComponent },
  { path: 'editadvertisement/:id', component: EditAdvertisementComponent },
  { path: 'createadvertisement', component: CreateAdvertisementComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
