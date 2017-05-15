import {RealEvent} from "./real-event";

export class TimePeriod {
  constructor(
    public timePeriodId: number,
    public event: RealEvent,
    public beginning: number,
    public end: number
  ){}
}
