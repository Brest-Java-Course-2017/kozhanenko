import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';
import { Location }                 from '@angular/common';

import {MyEvent} from "../models/event";
import { EventsService } from '../services/events.service';

import 'rxjs/add/operator/switchMap';
import {Subject} from 'rxjs/Subject';
import {Category} from "../../categories/models/category";
import {EventsTimePeriod} from "../models/eventsTimePeriod";
import {CategoryService} from "../../categories/category.service";
import {TimePeriod} from "../models/timePeriod";
import {DateConverterService} from "../services/date-converter.service";

@Component({
  selector: 'event-updating',
  templateUrl: `./event-updating.component.html`
})
export class EventUpdatingComponent implements OnInit{

  private _success = new Subject<string>();
  private _error = new Subject<string>();
  errorMessage: string;
  successMessage: string;
  categories: Category[];
  private selectedCategory: Category = new Category(-1, "", true, "");
  newEventName: string = "";
  newEventPlaceName: string = "";
  timePeriods: EventsTimePeriod [] = [];
  realTimePeriods: TimePeriod [];
  selectedBeginningTime: any = null;
  selectedBeginningDate: any = null;
  selectedEndTime: any = null;
  selectedEndDate: any = null;
  id: number;
  event: MyEvent;

  constructor(
    private eventsService: EventsService,
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private dateConverterService: DateConverterService
  ) {}

  ngOnInit(): void {
    this._success.subscribe((message) => this.successMessage = message);
    this._error.subscribe((message) => this.errorMessage = message);
    this.getCategories();
    this.route.params
      .switchMap((params: Params) => this.eventsService.getEvent(+params['id']))
      .subscribe(res => {
          this.errorMessage = res.errorMessage;
          if ( ! this.errorMessage){
            this.realTimePeriods = res.data;
            for (let realTimePeriod of this.realTimePeriods){
              this.timePeriods.push(new EventsTimePeriod(
                realTimePeriod.beginning,
                this.dateConverterService.convertDateFromSecondsToString(realTimePeriod.beginning),
                realTimePeriod.end,
                this.dateConverterService.convertDateFromSecondsToString(realTimePeriod.end)
              ));
            }
            this.selectedCategory.categoryId = this.realTimePeriods[0].event.category.categoryId;
            for (let category of this.categories){
              if (category.categoryId == this.selectedCategory.categoryId){
                this.selectedCategory.categoryName = category.categoryName;
              }
            }
            this.newEventName = this.realTimePeriods[0].event.eventName;
            this.newEventPlaceName = this.realTimePeriods[0].event.eventPlace;

          }
        },
        error =>  this.errorMessage = <any>error);
  }


  getCategories(){
    this.categoryService.getCategories()
      .subscribe(
        res1 => {
          this.categories = res1.data;
          this.errorMessage = res1.errorMessage;
          for (let category of this.categories) {
            category.placeholder = category.categoryName;
          }
        },
        error =>  this.errorMessage = <any>error);
  }

  setNewEventName(event: any){

  }


  setNewEventPlaceName(event: any){

  }

  updateEvent(){

  }

}
