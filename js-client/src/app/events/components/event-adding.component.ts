import {Component, OnInit} from '@angular/core';
import { Location } from '@angular/common';
import {Subject} from 'rxjs/Subject';
import {EventsService} from "../services/events.service";
import {Category} from "../../categories/models/category";
import { CategoryService } from '../../categories/category.service';
import {EventsTimePeriod} from "../models/eventsTimePeriod";
import {DateConverterService} from "../services/date-converter.service";
import {RealCategory} from "../../categories/models/real-category";
import {RealEvent} from "../models/real-event";

@Component({
  selector: 'my-events',
  templateUrl: './event-adding.component.html',
})
export class EventAddingComponent implements OnInit{

  private _success = new Subject<string>();
  private _error = new Subject<string>();
  errorMessage: string;
  successMessage: string;
  categories: Category[];
  private INITIAL_SELECTION: Category = new Category(-1, "Выберите категорию", true, "");
  private selectedCategory: Category = this.INITIAL_SELECTION;
  newEventName: string = "";
  newEventPlaceName: string = "";
  timePeriods: EventsTimePeriod [] = [];
  selectedBeginningTime: any = null;
  selectedBeginningDate: any = null;
  selectedEndTime: any = null;
  selectedEndDate: any = null;


  constructor(private location: Location,
              private eventsService: EventsService,
              private categoryService: CategoryService,
              private dateConverterService: DateConverterService) {}

  ngOnInit() {
    this.getCategories();
    this._success.subscribe((message) => this.successMessage = message);
    this._error.subscribe((message) => this.errorMessage = message);
  }

  getCategories() {
    this.categoryService.getCategories()
      .subscribe(
        res => {
          this.categories = res.data;
          this.errorMessage = res.errorMessage;
          for (let category of this.categories) {
            category.placeholder = category.categoryName;
          }
        },
        error =>  this.errorMessage = <any>error);
  }

  SetSelectedCategory(category: Category){
    this.selectedCategory = category;
  }

  setNewEventPlaceName(event: any){
    this.newEventPlaceName = event.target.value;
  }

  setNewEventName(event: any){
    this.newEventName = event.target.value;
  }

  removeTimePeriod(index: number){
    this.timePeriods.splice(index, 1);
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

  addNewEvent(){
    if (this.selectedCategory == this.INITIAL_SELECTION){
      this.errorMessage = "Вы не выбрали категорию события из выпадающего списка";
    } else if (this.newEventName.length < 2){
      this.errorMessage = "Наименование события должно иметь не менее 2 символов";
    } else if (this.newEventPlaceName.length < 2){
      this.errorMessage = "Наименование места проведения события должно иметь не менее 2 символов";
    } else if (this.timePeriods.length == 0){
      this.errorMessage = "Вы не указали ни одного периода проведения мероприятия";
    } else {
      let realCategory: RealCategory = new RealCategory(this.selectedCategory.categoryId,
        this.selectedCategory.categoryName);


      this.eventsService.create(
        new RealEvent(-1, realCategory, this.newEventName, this.newEventPlaceName),
        this.timePeriods)
        .subscribe(
        res => {
          this.successMessage = res.successMessage;
          this.errorMessage = res.errorMessage;
          if (this.successMessage){
            this.selectedCategory = this.INITIAL_SELECTION;
            this.newEventName = "";
            this.newEventPlaceName= "";
            this.timePeriods = [];
            this.selectedBeginningTime = null;
            this.selectedBeginningDate = null;
            this.selectedEndTime = null;
            this.selectedEndDate = null;
          }
        },
        error =>  this.errorMessage = <any>error);
    }
  }

  goBack(): void {
    this.location.back();
  }


}
