import {Component, OnInit} from '@angular/core';
import {Category} from "./models/category";
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
        res => {
          this.categories = res.data;
          this.errorMessage = res.errorMessage;
        },
        error =>  this.errorMessage = <any>error);
  }

  addCategory(name: string) {
    let categoryId: number;
    if (!name) { return; }
    this.categoryService.create(name)
      .subscribe(
        res => {
          this.successMessage = res.successMessage;
          this.errorMessage = res.errorMessage;
          categoryId = res.data;
          if (categoryId){
            this.categories.push(new Category(categoryId, name));
          }
        },
        error =>  this.errorMessage = <any>error);
  }

  deleteCategory(categoryId: string) {
    if (!categoryId) { return; }
    this.categoryService.deleteCategory(categoryId)
      .subscribe(
        res => {
          this.successMessage = res.successMessage;
          this.errorMessage = res.errorMessage;
          if (this.successMessage){
            let ind: number = 0;
            let index: number = -1;
            for (let entry of this.categories) {
              if (entry.categoryId.toString() == categoryId){
                index = ind;
              }
              ind++;
            }
            if (index > -1) {
              this.categories.splice(index, 1);
            }
          }
        },
        error =>  this.errorMessage = <any>error);
  }

}

