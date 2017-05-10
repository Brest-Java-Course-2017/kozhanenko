import {MyEvent} from "./event";

export class TimePeriod {
  constructor(
    private timePeriodId: number,
    private event: MyEvent,
    private beginning: number,
    private end: number
  ){}
}
