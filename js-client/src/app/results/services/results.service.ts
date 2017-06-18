import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import {ResponseGCAC} from "../models/response-get-categories-with-count-model";
import {ResponseGEoCiI} from "../models/response-get-events-of-category-in-interval-model";

@Injectable()
export class ResultsService {

  private categoriesUrl = 'http://localhost:8080/rest-app-1.0-SNAPSHOT/categories';
  private eventsUrl = 'http://localhost:8080/rest-app-1.0-SNAPSHOT/events';

  private typeOfSelection: string = "dropDown";//dropDown/datePicker
  private dropDownSelection: number = 0;//0-3
  private datePickerSelection: any;

  constructor (private http: Http) {}

  getTypeOfSelection(){
    return this.typeOfSelection;
  }

  getDropDownSelection(){
    return this.dropDownSelection;
  }

  getDatePickerSelection(){
    return this.datePickerSelection;
  }

  setDropDownSelection(val: number){
    this.typeOfSelection = "dropDown";
    this.dropDownSelection = val;
  }

  setDatePickerSelection(val: any){
    this.typeOfSelection = "datePicker";
    this.datePickerSelection = val;
  }

  getCategoriesAndCount(beginning: number, end: number): Observable<ResponseGCAC> {
    let headers = new Headers({ 'Content-Type': 'application/json',  'Access-Control-Allow-Origin': '*' });
    let options = new RequestOptions({ headers: headers });

    return this.http.get(this.categoriesUrl + "/" + beginning  + "/" + end, options)
      .map(this.extractData)
      .catch(this.handleError);
  }

  getEventsOfCategoryInInterval(categoryId: number, beginning: number, end: number): Observable<ResponseGEoCiI>{
    let headers = new Headers({ 'Content-Type': 'application/json',  'Access-Control-Allow-Origin': '*' });
    let options = new RequestOptions({ headers: headers });

    return this.http.get(this.eventsUrl  + "/" + categoryId + "/" + beginning  + "/" + end, options)
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

