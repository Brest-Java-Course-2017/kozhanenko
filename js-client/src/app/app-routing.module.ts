import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CategoriesComponent} from "./categories/categories.component";
import {ResultsComponent} from "./results/results.component";
import {EventsComponent} from "./events/events.component";


const routes: Routes = [
  { path: '', redirectTo: '/categories', pathMatch: 'full' },
  { path: 'categories',  component: CategoriesComponent },
  { path: 'results', component: ResultsComponent },
  { path: 'events',  component: EventsComponent }
];


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
