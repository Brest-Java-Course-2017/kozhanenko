import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';

import {MyEvent} from "../models/event";
import { EventsService } from '../services/events.service';

import 'rxjs/add/operator/switchMap';
import {Subject} from 'rxjs/Subject';
import {Category} from "../../categories/models/category";
import {EventsTimePeriod} from "../models/eventsTimePeriod";
import {CategoryService} from "../../categories/category.service";
import {TimePeriod} from "../models/timePeriod";
import {DateConverterService} from "../services/date-converter.service";
import {RealCategory} from "../../categories/models/real-category";
import {RealEvent} from "../models/real-event";

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
  eventId: number;
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
            if(this.realTimePeriods[0].timePeriodId !=0){
              for (let realTimePeriod of this.realTimePeriods){
                this.timePeriods.push(new EventsTimePeriod(
                  realTimePeriod.beginning,
                  this.dateConverterService.convertDateFromSecondsToString(realTimePeriod.beginning),
                  realTimePeriod.end,
                  this.dateConverterService.convertDateFromSecondsToString(realTimePeriod.end)
                ));
              }
            }

            this.selectedCategory.categoryId = this.realTimePeriods[0].event.category.categoryId;
            for (let category of this.categories){
              if (category.categoryId == this.selectedCategory.categoryId){
                this.selectedCategory.categoryName = category.categoryName;
              }
            }
            this.newEventName = this.realTimePeriods[0].event.eventName;
            this.newEventPlaceName = this.realTimePeriods[0].event.eventPlace;
            this.eventId = this.realTimePeriods[0].event.eventId;
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
    this.newEventName = event.target.value;
  }

  setNewEventPlaceName(event: any){
    this.newEventPlaceName = event.target.value;
  }

  pushNewTimePeriod(){
    if (this.selectedBeginningTime != null && this.selectedBeginningDate != null &&
      this.selectedEndTime != null && this.selectedEndDate != null){
      if (this.dateConverterService.convertDateFromMapToSeconds(this.selectedBeginningDate, this.selectedBeginningTime) <
        this.dateConverterService.convertDateFromMapToSeconds(this.selectedEndDate, this.selectedEndTime)){
        this.timePeriods.push(new EventsTimePeriod(
          this.dateConverterService.convertDateFromMapToSeconds(this.selectedBeginningDate, this.selectedBeginningTime),
          this.dateConverterService.convertDateFromMapToString(this.selectedBeginningDate, this.selectedBeginningTime),
          this.dateConverterService.convertDateFromMapToSeconds(this.selectedEndDate, this.selectedEndTime),
          this.dateConverterService.convertDateFromMapToString(this.selectedEndDate, this.selectedEndTime)
        ));
        this.selectedBeginningTime = null;
        this.selectedBeginningDate = null;
        this.selectedEndTime = null;
        this.selectedEndDate = null;
      } else {
        this.errorMessage = "Время начала события позже либо идентично времени окончания события";
      }
    } else {
      this.errorMessage = "Данные о периоде проведения события заполнены не полностью";
    }
  }

  removeTimePeriod(index: number){
    this.timePeriods.splice(index, 1);
  }

  updateEvent(){
    if (this.newEventName.length < 2){
      this.errorMessage = "Наименование события должно иметь не менее 2 символов";
    } else if (this.newEventPlaceName.length < 2){
      this.errorMessage = "Наименование места проведения события должно иметь не менее 2 символов";
    } else if (this.timePeriods.length == 0){
      this.errorMessage = "Вы не указали ни одного периода проведения мероприятия";
    } else {
      let realCategory: RealCategory = new RealCategory(this.selectedCategory.categoryId,
        this.selectedCategory.categoryName);
        this.eventsService.update(
          new RealEvent(this.eventId, realCategory, this.newEventName, this.newEventPlaceName),
          this.timePeriods)
          .subscribe(
            res => {
              this.successMessage = res.successMessage;
              this.errorMessage = res.errorMessage;
            },
            error =>  this.errorMessage = <any>error);
    }
  }

}
