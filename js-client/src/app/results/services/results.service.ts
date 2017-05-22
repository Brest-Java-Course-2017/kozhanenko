import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import {ResponseGCAC} from "../models/response-get-categories-with-count-model";

@Injectable()
export class ResultsService {

  private categoriesUrl = 'http://localhost:8090/categories';

  constructor (private http: Http) {}

  getCategoriesAndCount(beginning: number, end: number): Observable<ResponseGCAC> {
    let headers = new Headers({ 'Content-Type': 'application/json',  'Access-Control-Allow-Origin': '*' });
    let options = new RequestOptions({ headers: headers });

    return this.http.get(this.categoriesUrl + "/" + beginning  + "/" + end, options)
      .map(this.extractData)
      .catch(this.handleError);
  }

  private extractData(res: Response) {
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

