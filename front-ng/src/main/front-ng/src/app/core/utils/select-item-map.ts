/**
 * this class file is a reusable place holder for Dropdown.
 * contains key - value pairs
 * 
 */
export class SelectItemMap {
    private _value = '';
    private _viewValue = '';
    /*
    constructor(private val: any, private vval: any) {
        this._value = val;
        this._viewValue = vval;
    } */

    constructor() {
        this._value = '';
        this._viewValue = '';
    }

    get value(): string {
        return this._value;
    }
    get viewValue(): string {
        return this._viewValue;
    }

    set value(val) {
        this._value = val;
    }

    set viewValue(vval) {
        this._viewValue = vval;
    }

}
