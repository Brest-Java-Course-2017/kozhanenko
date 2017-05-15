import {TimePeriod} from "./timePeriod";

export class ResponseGFE {
  constructor(
    public data: TimePeriod[],
    public errorMessage: string,
  ){}
}
