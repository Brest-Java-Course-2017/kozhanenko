import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CategoriesComponent} from "./categories/categories.component";
import {ResultsComponent} from "./results/results.component";
import {EventsComponent} from "./events/components/events.component";
import {EventComponent} from "./events/components/event.component";


const routes: Routes = [
  { path: '', redirectTo: '/events', pathMatch: 'full' },
  { path: 'categories',  component: CategoriesComponent },
  { path: 'results', component: ResultsComponent },
  { path: 'events',  component: EventsComponent },
  { path: 'event',  component: EventComponent },
];


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
