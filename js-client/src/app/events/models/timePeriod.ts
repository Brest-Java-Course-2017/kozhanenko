import {RealEvent} from "./real-event";

export class TimePeriod {
  constructor(
    private timePeriodId: number,
    private event: RealEvent,
    private beginning: number,
    private end: number
  ){}
}
