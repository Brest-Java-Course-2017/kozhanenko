import {Component, OnInit} from '@angular/core';
import {ResultsService} from "../services/results.service";
import {CategoryWithCounts} from "../models/category-with-counts";
import {ConverterService} from "../services/converter.service";
import {Subject} from 'rxjs/Subject';
import 'rxjs/add/operator/debounceTime';

@Component({
  selector: 'my-results',
  templateUrl: './results.component.html',
})
export class ResultsComponent implements OnInit{

  private currentTime: Date;
  private dropdownIntervalsNames: string[] = ["Ближайший час", "Ближайшие 2 часа", "Сегодня", "Завтра"];
  selectedInterval: string = "Ближайший час";
  selectedEventsDate: any = null;
  categoriesWithCount: CategoryWithCounts[] = [];
  private _error = new Subject<string>();
  errorMessage: string;
  total: number;
  beginningOfPeriod: number;
  endOfPeriod: number;

  date: any;

  constructor(private resultsService: ResultsService,
              private converterService: ConverterService) {}

  ngOnInit() {
    this._error.subscribe((message) => this.errorMessage = message);
    let intervalPoints: number[] = this.converterService
      .getBeginningAndEndOfInterval(new Date(), 0);
    this.getCategoriesWithCountListAccordingToDropdown(intervalPoints[0], intervalPoints[1]);
  }

  SetSelectedInterval(selectedDropdownInterval: number){
    this.selectedInterval = this.dropdownIntervalsNames[+selectedDropdownInterval];
    this.currentTime = new Date();
    let intervalPoints: number[] = this.converterService
      .getBeginningAndEndOfInterval(this.currentTime, selectedDropdownInterval);
    this.getCategoriesWithCountListAccordingToDropdown(intervalPoints[0], intervalPoints[1]);
    this.selectedEventsDate = null;
  }

  SetSelectedIntervalForDate(){
    let intervalPoints: number[] = this.converterService.getIntervalForCertainDate(this.selectedEventsDate);
    this.getCategoriesWithCountListAccordingToDropdown(intervalPoints[0], intervalPoints[1]);
    this.selectedInterval = "Выберите период";
  }

  private getCategoriesWithCountListAccordingToDropdown(beginningOfPeriod: number, endOfPeriod: number){
      this.resultsService.getCategoriesAndCount(beginningOfPeriod, endOfPeriod)
        .subscribe(
          res => {
            this.categoriesWithCount = res.data;
            this.errorMessage = res.errorMessage;
            let total: number = 0;
            if (this.categoriesWithCount){
              for (let categoryWithCount of this.categoriesWithCount) {
                total += categoryWithCount.countEventsOfCategory;
              }
            }
            this.total = total;
            this.beginningOfPeriod = beginningOfPeriod;
            this.endOfPeriod = endOfPeriod;
          },
          error =>  this.errorMessage = <any>error);
  }
}
