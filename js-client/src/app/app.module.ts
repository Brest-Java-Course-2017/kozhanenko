import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule }     from '@ng-bootstrap/ng-bootstrap';//for bootstrap supporting
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';
import { CategoriesModule } from './categories/categories.module';
import { MyEventsModule } from './events/events.module';

import { AppComponent }  from './app.component';
//import { CategoriesComponent } from './categories/categories.component';
import { EventsComponent } from './events/components/events.component';
import { EventAddingComponent } from './events/components/event-adding.component';
import { EventUpdatingComponent } from './events/components/event-updating.component';
import { ResultsComponent } from './results/components/results.component'
import { NgbdDatepickerPopup } from './common-components/datepicker-popup';


//import { CategoryService } from './categories/category.service';
import { EventsService } from './events/services/events.service';
import {DatePipe} from "@angular/common";
import {ResultsModule} from "./results/results.module";
import {ResultEventsComponent} from "./results/components/result-events.component";

@NgModule({
  imports:      [
    NgbModule.forRoot(),
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    JsonpModule,
    CategoriesModule,
    MyEventsModule,
    ResultsModule,
  ],
  declarations: [
    AppComponent,
    //CategoriesComponent,
    EventsComponent,
    EventAddingComponent,
    EventUpdatingComponent,
    ResultsComponent,
    NgbdDatepickerPopup,
    //EventsOfCategoryInIntervalComponent
    ResultEventsComponent
  ],
  providers: [ DatePipe ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
