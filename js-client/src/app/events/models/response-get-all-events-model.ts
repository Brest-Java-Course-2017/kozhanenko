import { MyEvent } from "./event";

export class ResponseGAE {
  constructor(
    public data: MyEvent[],
    public errorMessage: string,
  ){}
}
