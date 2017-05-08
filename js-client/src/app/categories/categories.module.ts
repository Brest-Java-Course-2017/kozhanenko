import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule }     from '@ng-bootstrap/ng-bootstrap';//for bootstrap supporting
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';


import { CategoriesComponent } from './categories.component';

import { CategoryService } from './category.service';

@NgModule({
  imports:      [
    NgbModule.forRoot(),
    BrowserModule,
    //AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    JsonpModule
  ],
  declarations: [
    CategoriesComponent,
  ],
  providers: [
    CategoryService,
  ],
  exports: [ CategoriesComponent ]
})
export class CategoriesModule { }
