import {CategoryWithCounts} from "./category-with-counts";

export class ResponseGCAC {
  constructor(
    public data: CategoryWithCounts[],
    public errorMessage: string,
  ){}
}
