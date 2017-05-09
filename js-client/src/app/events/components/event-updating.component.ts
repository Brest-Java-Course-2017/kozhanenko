import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';
import { Location }                 from '@angular/common';

import {MyEvent} from "../models/event";
import { EventsService } from '../services/events.service';

import 'rxjs/add/operator/switchMap';

@Component({
  selector: 'event-updating',
  templateUrl: `./event-updating.component.html`
})
export class EventUpdatingComponent implements OnInit{

  event: MyEvent;

  constructor(
    private eventsService: EventsService,
    private route: ActivatedRoute,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.route.params
      .switchMap((params: Params) => this.eventsService.getEvent(+params['id']))
      .subscribe(event => this.event = event);
  }



  goBack(): void {
    this.location.back();
  }

}
