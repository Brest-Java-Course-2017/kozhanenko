import { Injectable } from '@angular/core';
import {Http, Headers, RequestOptions, Response} from "@angular/http";
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import {ResponseGAE} from "../models/response-get-all-events-model";
import {MyEvent} from "../models/event";
import {ResponseCE} from "../models/response-create-event-model";
import {EventsTimePeriod} from "../models/eventsTimePeriod";
import {TimePeriod} from "../models/timePeriod";

@Injectable()
export class EventsService {
  //TODO: solve Access-Control-Allow-Origin problem

  private eventsUrl = 'http://localhost:8090/events';
  private eventUrl = 'http://localhost:8090/event';
  //private eventsMockUrl = 'res.txt';

  constructor (private http: Http) {}

  getEvents(): Observable<ResponseGAE> {
    let headers = new Headers({ 'Content-Type': 'application/json',  'Access-Control-Allow-Origin': '*'  });
    let options = new RequestOptions({ headers: headers });

    return this.http.get(this.eventsUrl, options)
      .map(this.extractData)
      .catch(this.handleError);
  }

  getEvent(eventId: number): Observable<MyEvent>{
    return null;
  }

  create(event: MyEvent, clientTimePeriods: EventsTimePeriod[]): Observable<ResponseCE> {
    let headers = new Headers({ 'Content-Type': 'application/json',  'Access-Control-Allow-Origin': '*'  });
    let options = new RequestOptions({ headers: headers });
    let timePeriods: TimePeriod [] = [];
    for (let clientTimePeriod of clientTimePeriods) {
      timePeriods.push(new TimePeriod(0, event, clientTimePeriod.eventBeginningTime, clientTimePeriod.eventEndTime));
    }
    return this.http.post(this.eventUrl, { timePeriods }, options)
      .map(this.extractData)
      .catch(this.handleError);
  }

  private extractData(res: Response) {
    console.log("");
    let body = res.json();
    return body || { };
  }

  private handleError (error: Response | any) {
    //TODO: make a remote logging infrastructure
    // In a real world app, you might use a remote logging infrastructure
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(errMsg);
  }


}
