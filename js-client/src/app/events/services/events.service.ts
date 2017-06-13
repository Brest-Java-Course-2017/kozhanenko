import { Injectable } from '@angular/core';
import {Http, Headers, RequestOptions, Response} from "@angular/http";
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import {ResponseGAE} from "../models/response-get-all-events-model";
import {ResponseCE} from "../models/response-create-event-model";
import {EventsTimePeriod} from "../models/eventsTimePeriod";
import {TimePeriod} from "../models/timePeriod";
import {RealEvent} from "../models/real-event";
import {ResponseGFE} from "../models/response-get-full-event-model";
import {ResponseUDE} from "../models/response-update-delete-event-model";

@Injectable()
export class EventsService {
  //TODO: solve Access-Control-Allow-Origin problem

  private eventsUrl = 'http://localhost:8090/events';
  private eventUrl = 'http://localhost:8090/event';
  //private eventsMockUrl = 'res3.txt';

  constructor (private http: Http) {}

  getEvents(): Observable<ResponseGAE> {
    let headers = new Headers({ 'Content-Type': 'application/json',  'Access-Control-Allow-Origin': '*'  });
    let options = new RequestOptions({ headers: headers });

    return this.http.get(this.eventsUrl, options)
      .map(this.extractData)
      .catch(this.handleError);
  }

  getEvent(eventId: number): Observable<ResponseGFE>{
    let headers = new Headers({ 'Content-Type': 'application/json',  'Access-Control-Allow-Origin': '*'  });
    let options = new RequestOptions({ headers: headers });

    return this.http.get(this.eventUrl+ "/" + eventId, options)
      .map(this.extractData)
      .catch(this.handleError);
  }

  create(event: RealEvent, clientTimePeriods: EventsTimePeriod[]): Observable<ResponseCE> {
    let headers = new Headers({ 'Content-Type': 'application/json',  'Access-Control-Allow-Origin': '*'  });
    let options = new RequestOptions({ headers: headers });

    let timePeriods: TimePeriod[] = new Array(clientTimePeriods.length);
    let i = 0;
    for (let clientTimePeriod of clientTimePeriods) {
      timePeriods[i] = new TimePeriod(0, event, clientTimePeriod.eventBeginningTime, clientTimePeriod.eventEndTime);
      i++;
    }
    return this.http.post(this.eventUrl, { timePeriods }, options)
      .map(this.extractData)
      .catch(this.handleError);
  }

  update(event: RealEvent, clientTimePeriods: EventsTimePeriod[]): Observable<ResponseUDE> {
    let headers = new Headers({ 'Content-Type': 'application/json',  'Access-Control-Allow-Origin': '*'  });
    let options = new RequestOptions({ headers: headers });

    let timePeriods: TimePeriod[] = new Array(clientTimePeriods.length);
    let i = 0;
    for (let clientTimePeriod of clientTimePeriods) {
      timePeriods[i] = new TimePeriod(0, event, clientTimePeriod.eventBeginningTime, clientTimePeriod.eventEndTime);
      i++;
    }
    return this.http.put(this.eventUrl+ "/" + event.eventId, { timePeriods }, options)
      .map(this.extractData)
      .catch(this.handleError);
  }

  deleteEvent(eventId: string): Observable<ResponseUDE>{
    let headers = new Headers({ 'Content-Type': 'application/json',  'Access-Control-Allow-Origin': '*'  });
    let options = new RequestOptions({ headers: headers });

    return this.http
      .delete(this.eventUrl + "/" + eventId, options)
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
      // const body = error.json() || '';
      // const err = body.error || JSON.stringify(body);
      // errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
      errMsg = "Проверьте соединение с сетью интернет либо обратитесь к администратору";
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(errMsg);
  }



}
