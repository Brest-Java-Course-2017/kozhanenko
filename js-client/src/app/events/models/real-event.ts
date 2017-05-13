import { RealCategory } from '../../categories/models/real-category';

export class RealEvent {
  constructor(
    public eventId: number,
    public category: RealCategory,
    public eventName: string,
    public eventPlace: string
  ){}
}
