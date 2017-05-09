import {Component, OnInit} from '@angular/core';
import {EventsService} from "../services/events.service";
import {MyEvent} from "../models/event";
import {Category} from "../../categories/models/category";

@Component({
  selector: 'my-events',
  templateUrl: './events.component.html',
})
export class EventsComponent implements OnInit{

  errorMessage: string;
  successMessage: string;
  events: MyEvent[] = [];
  //
  constructor (private eventsService: EventsService) {}
  //
  ngOnInit() {
    //this.events.push(new MyEvent(1, new Category(1, "sadas", true, "dsfdf"), "sdasd", "sdasd"));
     this.getEvents();
  }
  //
  getEvents() {
    this.eventsService.getEvents()
      .subscribe(
        res => {
          this.events = res.data;
        },
        error =>  this.errorMessage = <any>error);
  }

}
