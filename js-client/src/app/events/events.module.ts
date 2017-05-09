import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule }     from '@ng-bootstrap/ng-bootstrap';//for bootstrap supporting
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';


import { EventsComponent } from './components/events.component';
import { EventComponent } from './components/event.component';

import { EventsService } from './services/events.service';

@NgModule({
  imports:      [
    NgbModule.forRoot(),
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    JsonpModule
  ],
  declarations: [
    //EventsComponent,
    //EventComponent
  ],
  providers: [
    EventsService,
  ],
  exports: [ ]//EventsComponent, EventComponent ]
})
export class EventsModule { }
