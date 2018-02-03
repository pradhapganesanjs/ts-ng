function notoverridable(target) {

    Object.defineProperty(target.prototype
                            , 'getColor'
                            , { value: function () { return this.color; }
                                , writable: false
                                , configurable: true
                                , enumerable:true });


}
@notoverridable
class Car {
    private color = "N/A";
    constructor(cr) {
        this.color = cr;
    }
}
const car = new Car('red');
console.log('B4 ' + car.getColor());
car.getColor = function () {
    return "getcolor nomore !!! :)";
}
//console.log(car.color);
console.log("A4 " + car.getColor());