import { Component } from '@angular/core';
import { Location }                 from '@angular/common';

@Component({
  selector: 'my-events',
  templateUrl: './event.component.html',
})
export class EventComponent{

  constructor(
    private location: Location
  ) {}

  goBack(): void {
    this.location.back();
  }


}
