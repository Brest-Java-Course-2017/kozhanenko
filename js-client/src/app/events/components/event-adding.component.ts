import {Component, OnInit} from '@angular/core';
import { Location } from '@angular/common';
import {Subject} from 'rxjs/Subject';
import {EventsService} from "../services/events.service";
import {Category} from "../../categories/models/category";
import { CategoryService } from '../../categories/category.service';
import {EventsTimePeriod} from "../models/eventsTimePeriod";
import {DateConverterService} from "../services/date-converter.service";

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
  newEventName: string;
  newEventPlaceName: string;
  timePeriods: EventsTimePeriod [] = [];
  selectedBeginningTime: {} = null;
  selectedBeginningDate: {} = null;
  selectedEndTime: {} = null;
  selectedEndDate: {} = null;


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

  }

  goBack(): void {
    this.location.back();
  }


}
