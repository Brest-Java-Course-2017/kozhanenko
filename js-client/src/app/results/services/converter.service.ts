import {Injectable} from "@angular/core";

@Injectable()
export class ConverterService {

  public getBeginningAndEndOfInterval(currentTime: Date, selectedDropdownInterval: number): number[]{

    let res: number[] = [0, 0];

    switch (selectedDropdownInterval){
      case 0:
        res[0] = Math.floor(+currentTime/1000);
        res[1] = Math.floor(+currentTime/1000 + 60*60);

        break;

      case 1:
        res[0] = Math.floor(+currentTime/1000);
        res[1] = Math.floor(+currentTime/1000 + 2*60*60);
        break;

      case 2:
        res[0] = Math.floor(+currentTime/1000);
        res[1] = Math.floor(+currentTime.setHours(23, 59, 59)/1000);
        break;

      case 3:
        let tomorrow: Date = new Date(+currentTime + 1000*60*60*24);
        res[0] = Math.floor(+tomorrow.setHours(0, 0, 0)/1000);
        res[1] = Math.floor(+tomorrow.setHours(23, 59, 59)/1000);
        break;
    }
    return res;
  }

  public getIntervalForCertainDate(fullYear: any): number[]{
    let res: number[] = [0, 0];
     let date1: number = new Date().setFullYear(fullYear.year, fullYear.month - 1, fullYear.day);
      res[0] = Math.floor(+(new Date(date1).setHours(0, 0, 0))/1000);
      res[1] = Math.floor(+(new Date(date1).setHours(23, 59, 59))/1000);
      return res;
  }


}
