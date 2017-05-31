export class ResultEvent {
  constructor(
    public eventName: string,
    public eventPlace: string,
    public eventBeginToNow: string,
    public eventBeginDateInString: string,
    public eventEndToNow: string,
    public eventEndDateInString: string
  ){}
}
