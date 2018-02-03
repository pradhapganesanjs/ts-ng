var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var gcolor = function (color) {
    return function (target) {
        target.l = color;
    };
};
var G = /** @class */ (function () {
    function G() {
    }
    G = __decorate([
        gcolor('pink')
    ], G);
    return G;
}());
console.log("g color is " + G.l);
var gi1 = new G();
console.log("gi1 color is " + gi1.l);
function print(target) {
    target.fullName = 'prad gane';
}
var Person = /** @class */ (function () {
    function Person(fn, ln) {
        this._fname = fn;
        this._lname = ln;
    }
    Object.defineProperty(Person.prototype, "fname", {
        get: function () {
            return this._fname;
        },
        set: function (fn) {
            this._fname = fn;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Person.prototype, "lname", {
        get: function () {
            return this._lname;
        },
        set: function (ln) {
            this._lname = ln;
        },
        enumerable: true,
        configurable: true
    });
    Person = __decorate([
        print
    ], Person);
    return Person;
}());
var pers = new Person('pradhap', 'ganesan');
console.log(' fname ' + Person.fullName);
