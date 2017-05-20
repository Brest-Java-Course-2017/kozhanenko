import {Component, OnInit} from '@angular/core';
import {EventsService} from "../services/events.service";
import {MyEvent} from "../models/event";
import {Subject} from 'rxjs/Subject';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

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
  private eventNameToDelete: string;
  private eventIdToDelete: number;

  constructor (private eventsService: EventsService,
               private modalService: NgbModal) {}

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

  setEventToDelete(eventId: number, eventName: string){
    this.eventIdToDelete = eventId;
    this.eventNameToDelete = eventName;
  }
  //modal window open
  open(content: any) {
    this.modalService.open(content);
  }

  deleteEvent(){
    let eventId: number = this.eventIdToDelete;
    if (!eventId) { return; }
    this.eventsService.deleteEvent(eventId.toString())
      .subscribe(
        res => {
          this.successMessage = res.successMessage;
          this.errorMessage = res.errorMessage;
          if (this.successMessage){
            let ind: number = 0;
            let index: number = -1;
            for (let entry of this.events) {
              if (entry.eventId == eventId){
                index = ind;
              }
              ind++;
            }
            if (index > -1) {
              this.events.splice(index, 1);
            }
            this.eventIdToDelete = 0;
            this.eventNameToDelete = null;
          }
        },
        error =>  this.errorMessage = <any>error);
  }

}
