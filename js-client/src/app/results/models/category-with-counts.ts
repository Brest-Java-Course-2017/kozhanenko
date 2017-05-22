import {Category} from "../../categories/models/category";

export class CategoryWithCounts {
  constructor(
    public category: Category,
    public count: number,
  ){}
}
