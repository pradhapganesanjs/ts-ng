var Student = /** @class */ (function () {
    function Student(name, dob, sex) {
        this.sumArr = function (arr) { return Student.sumArrStat(arr); };
        this._name = name;
        this._dob = dob;
        this._sex = sex;
        this._nameid = (this._sex ? this._sex : '') + "_" + this._name + "_" + this._dob;
    }
    Student.defaultStudent = function () {
        return new Student('Pg', '12/12/1988', 'M');
    };
    Student.sumArrStat = function (arr) {
        var sum = 0;
        for (var _i = 0, arr_1 = arr; _i < arr_1.length; _i++) {
            var v = arr_1[_i];
            sum += v;
        }
        return sum;
    };
    return Student;
}());
console.log("constructor :" + new Student('Pradhap', '11/11/1988', 'Male'));
console.log("static : " + Student.defaultStudent());
var s1 = new Student('Ganesan', '11/11/1944', 'M');
console.log(s1.sumArr([11, 22, 33]));
