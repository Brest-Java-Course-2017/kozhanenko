import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CategoriesComponent} from "./categories/categories.component";
import {ResultsComponent} from "./results/components/results.component";
import {EventsComponent} from "./events/components/events.component";
import {EventAddingComponent} from "./events/components/event-adding.component";
import {EventUpdatingComponent} from './events/components/event-updating.component';
import {ResultEventsComponent} from "./results/components/result-events.component";


const routes: Routes = [
  { path: '', redirectTo: '/results', pathMatch: 'full' },
  { path: 'categories',  component: CategoriesComponent },
  { path: 'results', component: ResultsComponent },
  { path: 'events',  component: EventsComponent },
  { path: 'event',  component: EventAddingComponent },
  { path: 'event/:id', component: EventUpdatingComponent},
  { path: 'results/:categoryId/:beginOdInterval/:endOfInterval', component: ResultEventsComponent}

];


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
