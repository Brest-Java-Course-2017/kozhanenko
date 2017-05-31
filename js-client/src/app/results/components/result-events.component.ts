import {Component, OnInit} from '@angular/core';
import {ResultsService} from "../services/results.service";
import {ConverterService} from "../services/converter.service";
import {Subject} from 'rxjs/Subject';
import 'rxjs/add/operator/debounceTime';
import {TimePeriod} from "../../events/models/timePeriod";
import {ResultEvent} from "../models/result-event-model";
import { ActivatedRoute, Params }   from '@angular/router';
import {DateConverterService} from "../../events/services/date-converter.service";

@Component({
  selector: 'my-result',
  templateUrl: './result-event.component.html',
})
export class ResultEventsComponent implements OnInit{

  private _error = new Subject<string>();
  errorMessage: string;
  resultEvents: ResultEvent[] = [];

  constructor(private resultsService: ResultsService,
              private route: ActivatedRoute,
              private converterService: ConverterService,
              private dateConverterService: DateConverterService) {}

  ngOnInit() {
    this._error.subscribe((message) => this.errorMessage = message);
    this.route.params
      .switchMap((params: Params) => this.resultsService.getEventsOfCategoryInInterval(
        +params['categoryId'], +params['beginOdInterval'], +params['endOfInterval']))
      .subscribe(res => {
          this.errorMessage = res.errorMessage;
          if ( ! this.errorMessage){
            let timePeriods: TimePeriod[] = res.data;
            if (timePeriods){
              for (let timePeriod of timePeriods) {
                this.resultEvents.push(new ResultEvent(
                  timePeriod.event.eventName,
                  timePeriod.event.eventPlace,
                  this.converterService.getPeriodToBeginning(new Date(), timePeriod.beginning, timePeriod.end),
                  this.dateConverterService.convertDateFromSecondsToString(timePeriod.beginning),
                  this.converterService.getPeriodToEnd(new Date(), timePeriod.end),
                  this.dateConverterService.convertDateFromSecondsToString(timePeriod.end)
                ));
              }
            }
          }
        },
        error =>  this.errorMessage = <any>error);
  }

}
