export class Category {
  constructor(
    public categoryId: number,
    public categoryName: string,
    public visibility: boolean = true,
    public placeholder: string
  ){}
}
