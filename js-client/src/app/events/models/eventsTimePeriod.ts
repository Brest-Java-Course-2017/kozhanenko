export class EventsTimePeriod {
  constructor(
    public eventTimePeriodId: number,
    public eventBeginningTime: number,
    public eventBeginningTimeInString: string,
    public eventEndTime: string,
    public eventEndTimeInString: string
  ){}
}
