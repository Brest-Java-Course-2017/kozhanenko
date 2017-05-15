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
    let date: Date = new Date(dateInSeconds);

    let month: string = date.getMonth() + 1 > 9 ? (date.getMonth() + 1).toString() : ("0" + (date.getMonth()+1));
    let day: string = date.getDay() + 1 > 9 ? (date.getDay() +1).toString() : ("0" + (date.getDay() + 1));
    let hour: string = date.getHours() > 9 ? date.getHours().toString() : ("0" + date.getHours());
    let minute: string = date.getMinutes() > 9 ? date.getMinutes().toString() : ("0" + date.getMinutes());

    return date.getFullYear() + "." + month + "." + day
      + " " + hour + ":" + minute;
  }

}
