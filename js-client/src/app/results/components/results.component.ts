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
  selectedInterval: string = "Выберите период";
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
    let typeOfSelection = this.resultsService.getTypeOfSelection();
    let dropDownSelection: number, intervalPoints: number[], datePickerSelection: any;
    if (typeOfSelection == "dropDown"){
      dropDownSelection = this.resultsService.getDropDownSelection();
      intervalPoints = this.converterService.getBeginningAndEndOfInterval(new Date(), dropDownSelection);
      this.getCategoriesWithCountList(intervalPoints);
      this.selectedInterval = this.dropdownIntervalsNames[dropDownSelection];
    }
    if (typeOfSelection == "datePicker"){
      datePickerSelection = this.resultsService.getDatePickerSelection();
      intervalPoints= this.converterService.getIntervalForCertainDate(datePickerSelection);
      this.getCategoriesWithCountList(intervalPoints);
      this.selectedEventsDate = datePickerSelection;
    }
    this.getCategoriesWithCountList(intervalPoints);
  }

  SetSelectedInterval(selectedDropdownInterval: number){
    this.selectedInterval = this.dropdownIntervalsNames[+selectedDropdownInterval];
    this.currentTime = new Date();
    let intervalPoints: number[] = this.converterService
      .getBeginningAndEndOfInterval(this.currentTime, selectedDropdownInterval);
    this.getCategoriesWithCountList(intervalPoints);
    this.selectedEventsDate = null;
    this.resultsService.setDropDownSelection(selectedDropdownInterval);
  }

  SetSelectedIntervalForDate(){
    let intervalPoints: number[] = this.converterService.getIntervalForCertainDate(this.selectedEventsDate);
    this.getCategoriesWithCountList(intervalPoints);
    this.selectedInterval = "Выберите период";
    this.resultsService.setDatePickerSelection(this.selectedEventsDate);
  }

  private getCategoriesWithCountList(intervalPoints: number[]){
      this.resultsService.getCategoriesAndCount(intervalPoints[0], intervalPoints[1])
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
            this.beginningOfPeriod = intervalPoints[0];
            this.endOfPeriod = intervalPoints[1];
          },
          error =>  this.errorMessage = <any>error);
  }
}
