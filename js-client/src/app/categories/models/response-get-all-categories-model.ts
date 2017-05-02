import {Category} from "./category";

export class ResponseGAC {
  constructor(
    public data: Category[],
    public errorMessage: string,
  ){}
}
