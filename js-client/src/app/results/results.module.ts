import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule }     from '@ng-bootstrap/ng-bootstrap';//for bootstrap supporting
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';


import { ResultsComponent } from './components/results.component';
import {ResultsService} from "./services/results.service";
import {ConverterService} from "./services/converter.service";

//import { CategoryService } from './category.service';

@NgModule({
  imports:      [
    NgbModule.forRoot(),
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    JsonpModule
  ],
  declarations: [
    //ResultsComponent,
  ],
  providers: [
    ConverterService,
    ResultsService,
  ],
  exports: [ ]//ResultsComponent ]
})
export class ResultsModule { }
