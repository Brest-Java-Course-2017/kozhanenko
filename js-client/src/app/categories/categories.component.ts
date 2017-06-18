import {Component, OnInit} from '@angular/core';
import {Category} from "./models/category";
import {CategoryService} from "./category.service";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Subject} from 'rxjs/Subject';
import 'rxjs/add/operator/debounceTime';

@Component({
  selector: 'my-categories',
  templateUrl: './categories.component.html',
})
export class CategoriesComponent implements OnInit{

  private _success = new Subject<string>();
  private _error = new Subject<string>();
  errorMessage: string;
  successMessage: string;
  categories: Category[];
  //mode = 'Observable';
  //show: boolean = true;
  private newCategoryNames: Map<number, string> = new Map();
  private categoryToDelete: number;
  private categoryNameToDelete: string;

  constructor (private categoryService: CategoryService, private modalService: NgbModal) {}

  ngOnInit() {
    this.getCategories();
    this._success.subscribe((message) => this.successMessage = message);
    this._error.subscribe((message) => this.errorMessage = message);
  }

  setUpdateValue(event: any, categoryId: number){
    this.newCategoryNames.set(categoryId, event.target.value);
  }

  setCategoryToDelete(categoryId: number, categoryName: string){
    this.categoryToDelete = categoryId;
    this.categoryNameToDelete = categoryName;
  }
  //modal window open
  open(content: any) {
    this.modalService.open(content);
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

  addCategory(name: string) {
    let categoryId: number;
    let visibility: boolean = false;
    if (!name) { return; }
    this.categoryService.create(name)
      .subscribe(
        res => {
          this.successMessage = res.successMessage;
          this.errorMessage = res.errorMessage;
          categoryId = res.data;
          if (categoryId){
            this.categories.push(new Category(categoryId, name, visibility, name));
          }
        },
        error =>  this.errorMessage = <any>error);
  }

  deleteCategory() {
    let categoryId: number = this.categoryToDelete;
    if (!categoryId) { return; }
    this.categoryService.deleteCategory(categoryId.toString())
      .subscribe(
        res => {
          this.successMessage = res.successMessage;
          this.errorMessage = res.errorMessage;
          if (this.successMessage){
            let ind: number = 0;
            let index: number = -1;
            for (let entry of this.categories) {
              if (entry.categoryId == categoryId){
                index = ind;
              }
              ind++;
            }
            if (index > -1) {
              this.categories.splice(index, 1);
            }
            this.newCategoryNames.delete(categoryId);
          }
        },
        error =>  this.errorMessage = <any>error);
  }

  updateCategory(categoryId: number){
      let initialCategoryName: string = "";
      let changeableCategory: Category;
      for (let category of this.categories) {
        if (category.categoryId == categoryId){
          initialCategoryName = category.categoryName;
          changeableCategory = category;
        }
      }

      if ( ! this.newCategoryNames.has(categoryId)){
        changeableCategory.visibility = !changeableCategory.visibility;
      } else if (this.newCategoryNames.get(categoryId).length < 2){
        //TODO: make loading this phrase from property file
        this.errorMessage = "Имя категории должно составлять не менее 2 символов."
      } else if (this.newCategoryNames.get(categoryId) == initialCategoryName){
        changeableCategory.visibility = !changeableCategory.visibility;
      } else {
        this.categoryService.updateCategory(categoryId.toString(), this.newCategoryNames.get(categoryId))
          .subscribe(
            res => {
              this.successMessage = res.successMessage;
              this.errorMessage = res.errorMessage;
              if (this.successMessage){
                for (let category of this.categories) {
                  if (category.categoryId == categoryId){
                    category.visibility = !category.visibility;
                    category.categoryName = this.newCategoryNames.get(categoryId);
                  }
                }
              }
            },
            error =>  this.errorMessage = <any>error);
      }
  }
}

