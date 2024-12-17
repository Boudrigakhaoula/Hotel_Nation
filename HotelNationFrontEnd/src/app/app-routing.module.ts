import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './layout/pages/home/home.component';
import { AboutComponent } from './layout/pages/about/about.component';
import { ServicesComponent } from './layout/pages/services/services.component';
import { DestinationComponent } from './layout/pages/destination/destination.component';
import { NotFoundComponent } from './layout/pages/not-found/not-found.component';

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'about', component:AboutComponent},
  { path: 'services', component: ServicesComponent},
  { path: 'destination', component: DestinationComponent },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
