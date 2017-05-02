import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import {ResponseGAC} from "./models/response-get-all-categories-model";
import {ResponseAC} from "./models/response-add-category-model";
import {ResponseUDC} from "./models/response-update-delete-category-model";
import {map} from "rxjs/operator/map";


@Injectable()
export class CategoryService {

  private categoriesUrl = 'http://localhost:8090/categories';
  private categoryUrl = 'http://localhost:8090/category';
  //private categoriesUrl = 'res.txt';

  constructor (private http: Http) {}

  getCategories(): Observable<ResponseGAC> {
    return this.http.get(this.categoriesUrl)
      .map(this.extractData)
      .catch(this.handleError);
  }

  create(categoryName: string): Observable<ResponseAC> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this.http.post(this.categoryUrl, { categoryName }, options)
      .map(this.extractData)
      .catch(this.handleError);
  }

  deleteCategory(categoryId: string): Observable<ResponseUDC>{
    //let headers = new Headers({ 'Content-Type': 'application/json' });
    //let options = new RequestOptions({ headers: headers });

    return this.http
      .delete(this.categoryUrl + "/" + categoryId)
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
