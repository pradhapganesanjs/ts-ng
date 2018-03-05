import { Component, OnInit } from '@angular/core';
import { Input, Output, EventEmitter} from '@angular/core';
import { SelectItemMap } from '@app/core';


@Component({
  selector: 'app-select-mat',
  templateUrl: './select-mat.component.html',
  styleUrls: ['./select-mat.component.css']
})
export class SelectMatComponent implements OnInit {

  @Input() itemsMap: SelectItemMap[];
  @Input() selectedVal: string;
  @Output() selectedItem = new EventEmitter<string>();


  constructor() { }

  ngOnInit() {
  }

  onChange(selected) {
    this.selectedItem.emit(selected.value);
  }

}
