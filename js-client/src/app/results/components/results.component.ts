import { Component } from '@angular/core';
import {ResultsService} from "../services/results.service";
import {CategoryWithCounts} from "../models/category-with-counts";
import {ConverterService} from "../services/converter.service";
import {Subject} from 'rxjs/Subject';
import 'rxjs/add/operator/debounceTime';

@Component({
  selector: 'my-results',
  templateUrl: './results.component.html',
})
export class ResultsComponent{

  private currentTime: Date;
  private dropdownIntervalsNames: Map<string, string>;// = new Map();
  selectedBeginning: string = "Ближайший час";
  selectedEventsDate: any = null;
  categoriesWithCount: CategoryWithCounts[] = [];
  private _error = new Subject<string>();
  errorMessage: string;

  constructor(private resultsService: ResultsService,
              private converterService: ConverterService) {}

  ngOnInit() {
    // this.dropdownIntervalsNames.set("nextHour", "Ближайший час");
    // this.dropdownIntervalsNames.set("nextTwoHours", "Ближайшие 2 часа");
    // this.dropdownIntervalsNames.set("today", "Сегодня");
    // this.dropdownIntervalsNames.set("tomorrow", "Завтра");
    this._error.subscribe((message) => this.errorMessage = message);
  }

  SetSelectedInterval(selectedDropdownInterval: string){

    this.selectedBeginning = this.dropdownIntervalsNames.get(selectedDropdownInterval);

    this.currentTime = new Date();

    let intervalPoints: number[] = this.converterService
      .getBeginningAndEndOfInterval(this.currentTime, selectedDropdownInterval);

    this.getCategoriesWithCountListAccordingToDropdown(intervalPoints[0], intervalPoints[1]);
  }

  private getCategoriesWithCountListAccordingToDropdown(beginningOfPeriod: number, endOfPeriod: number){
      this.resultsService.getCategoriesAndCount(beginningOfPeriod, endOfPeriod)
        .subscribe(
          res => {
            this.categoriesWithCount = res.data;
            this.errorMessage = res.errorMessage;
          },
          error =>  this.errorMessage = <any>error);
    }
}
