import {TimePeriod} from "../../events/models/timePeriod";

export class ResponseGEoCiI {
  constructor(
    public data: TimePeriod[],
    public errorMessage: string,
  ){}
}
