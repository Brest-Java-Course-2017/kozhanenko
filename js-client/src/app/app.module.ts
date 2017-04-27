import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule }     from '@ng-bootstrap/ng-bootstrap';//for bootstrap supporting
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { JsonpModule } from '@angular/http';

import { AppComponent }  from './app.component';
import { CategoriesComponent } from './categories/categories.component';
import { EventsComponent } from './events/events.component';
import { EventComponent } from './events/event.component';
import { ResultsComponent } from './results/results.component'
import { NgbdDatepickerPopup } from './common-components/datepicker-popup';

@NgModule({
  imports:      [
    NgbModule.forRoot(),
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    JsonpModule
  ],
  declarations: [
    AppComponent,
    CategoriesComponent,
    EventsComponent,
    EventComponent,
    ResultsComponent,
    NgbdDatepickerPopup
  ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
