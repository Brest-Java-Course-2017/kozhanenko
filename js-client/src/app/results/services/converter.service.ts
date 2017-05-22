import {Injectable} from "@angular/core";

@Injectable()
export class ConverterService {

  public getBeginningAndEndOfInterval(currentTime: Date, selectedDropdownInterval: string): number[]{

    let res: number[] = [0, 0];

    switch (selectedDropdownInterval){
      case "nextHour":
        res[0] = +currentTime/1000;
        res[1] = +currentTime/1000 + 60*60;
        break;

      case "nextTwoHours":
        res[0] = +currentTime/1000;
        res[1] = +currentTime/1000 + 2*60*60;
        break;

      case "today":
        //TODO: make it
        break;

      case "tomorrow":
        //TODO: make it
        break;
    }
    return res;
  }
}
