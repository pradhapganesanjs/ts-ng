// let funcreadonly = function (target,key,descriptor){
//     descriptor.writable=false;
//     return descriptor;
// }
//const descriptor = funcreadonly(target.prototype,key,descriptor);
//Object.defineProperty(target.prototype,key,descriptor) || descriptor;
var funcreadonly = function (target, key, descriptor) {
    console.log('target : ' + target);
    console.log('key : ' + key);
    //descriptor = funcreadonly(target.prototype,key,descriptor);
    var desc = Object.defineProperty(target, key, { writable: false });
    console.log('descriptor ' + desc);
    //desc.writable = false;
    return desc;
};
var Car = /** @class */ (function () {
    function Car() {
        this.model = "Basic";
    }
    //@funcreadonly
    Car.prototype.updateModel = function () {
        return this.model;
    };
    return Car;
}());
// const c1 = new Car();
// c1.updateModel("Sports");
// console.log('c1 '+c1.model);
Car.prototype.updateModel = function (x) {
    this.model = "car model is HACKED...";
};
var c12 = new Car();
c12.updateModel("Sports");
console.log('c12 ' + c12.model);
