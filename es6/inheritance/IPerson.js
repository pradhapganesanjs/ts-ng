"use strict";
exports.__esModule = true;
var TestPerson = /** @class */ (function () {
    function TestPerson() {
        //this.p = new Person();
    }
    TestPerson.prototype.test = function () {
        var ip = { fname: '' };
        var props = Object.getOwnPropertyNames(ip);
        props.forEach(function (p) { return console.log(p); });
    };
    return TestPerson;
}());
var Person = /** @class */ (function () {
    function Person() {
        this.dob = undefined;
        this.fname = undefined;
        this.lname = undefined;
    }
    Object.defineProperty(Person.prototype, "dob", {
        get: function () {
            return this.dob;
        },
        set: function (dob) {
            this.dob = dob;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Person.prototype, "fname", {
        get: function () {
            return this.fname;
        },
        set: function (fn) {
            this.fname = fn;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Person.prototype, "lname", {
        get: function () {
            return this.lname;
        },
        set: function (ln) {
            this.lname = ln;
        },
        enumerable: true,
        configurable: true
    });
    return Person;
}());
exports.Person = Person;
var t = new TestPerson();
t.test();
