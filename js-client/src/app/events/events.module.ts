import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule }     from '@ng-bootstrap/ng-bootstrap';//for bootstrap supporting
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';


import { EventsComponent } from './components/events.component';
import { EventAddingComponent } from './components/event-adding.component';
import { EventUpdatingComponent } from './components/event-updating.component';

import { EventsService } from './services/events.service';
import {DateConverterService} from "./services/date-converter.service";

@NgModule({
  imports: [
    NgbModule.forRoot(),
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    JsonpModule,
  ],
  declarations: [
    //EventsComponent,
    //EventAddingComponent,
    //EventUpdatingComponent
  ],
  providers: [
    EventsService,
    DateConverterService
  ],
  exports: [ ]//EventsComponent, EventAddingComponent, EventUpdatingComponent  ]
})
export class MyEventsModule { }
