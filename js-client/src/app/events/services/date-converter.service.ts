import {Injectable} from "@angular/core";

@Injectable()
export class DateConverterService {

  public convertDateFromMapToSeconds(fullYear: any, time: any): number {
    //fullYear.month - in human format
    let date1: number = new Date().setFullYear(fullYear.year, fullYear.month - 1, fullYear.day);
    return new Date(date1).setHours(time.hour, time.minute, 0);
  }

  public convertDateFromMapToString(fullYear: any, time: any): string {
    let month: string = fullYear.month > 9 ? fullYear.month.toString() : ("0" + fullYear.month);
    let day: string = fullYear.day > 9 ? fullYear.day.toString() : ("0" + fullYear.day);
    let hour: string = time.hour > 9 ? time.hour.toString() : ("0" + time.hour);
    let minute: string = time.minute > 9 ? time.minute.toString() : ("0" + time.minute);
    return fullYear.year + "." + month + "." + day + " " + hour + ":" + minute;
  }

  public convertDateFromSecondsToString(dateInSeconds: number): string{
    return null;
  }

}
