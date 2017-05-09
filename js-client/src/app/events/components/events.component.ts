import {Component, OnInit} from '@angular/core';
import {EventsService} from "../services/events.service";
import {MyEvent} from "../models/event";
import {Subject} from 'rxjs/Subject';

@Component({
  selector: 'my-events',
  templateUrl: './events.component.html',
})
export class EventsComponent implements OnInit{

  private _success = new Subject<string>();
  private _error = new Subject<string>();
  errorMessage: string;
  successMessage: string;
  events: MyEvent[] = [];
  //
  constructor (private eventsService: EventsService) {}
  //
  ngOnInit() {
    this.getEvents();
    this._success.subscribe((message) => this.successMessage = message);
    this._error.subscribe((message) => this.errorMessage = message);
  }

  getEvents() {
    this.eventsService.getEvents()
      .subscribe(
        res => {
          this.events = res.data;
        },
        error =>  this.errorMessage = <any>error);
  }

}
