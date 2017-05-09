import { Category } from '../../categories/models/category';

export class MyEvent {
  constructor(
    public eventId: number,
    public category: Category,
    public eventName: string,
    public eventPlace: string
  ){}
}
