import {Component, OnInit} from '@angular/core';
import {Category} from "./category";
import {CategoryService} from "./category.service";

@Component({
  selector: 'my-categories',
  templateUrl: './categories.component.html',
})
export class CategoriesComponent implements OnInit{

  errorMessage: string;
  successMessage: string;
  categories: Category[];
  mode = 'Observable';

  constructor (private categoryService: CategoryService) {}

  ngOnInit() {
    this.getCategories();
  }

  getCategories() {
    this.categoryService.getCategories()
      .subscribe(
        categories => this.categories = categories,
        error =>  this.errorMessage = <any>error);
  }

  addCategory(name: string) {
    if (!name) { return; }
    this.categoryService.create(name)
      .subscribe(
        //category  => this.categories.push(category),
        error =>  this.errorMessage = <any>error);

    this.getCategories();
  }

}

