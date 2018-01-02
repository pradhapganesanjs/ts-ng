class Student{
    
    _name; _dob; _sex; _nameid

    constructor(name, dob, sex){
        this._name=name;
        this._dob=dob;
        this._sex=sex;
        this._nameid = `${this._sex?this._sex:''}_${this._name}_${this._dob}`
    }

    static defaultStudent(){
        return new Student('Pg','12/12/1988','M')
    }

    static sumArrStat = (arr) => {
        let sum = 0;
        for(let v of arr){
            sum += v;
        }
        return sum;
    }

    sumArr = (arr) => Student.sumArrStat(arr);

}

console.log(`constructor :${new Student('Pradhap','11/11/1988','Male')}`)
console.log(`static : ${Student.defaultStudent()}`)

let s1 = new Student('Ganesan','11/11/1944','M');
console.log(s1.sumArr([11,22,33]));