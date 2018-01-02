class Person{
    
    constructor(name, dob, sex){
        this._name=name;
        this._dob=dob;
        this._sex=sex;
        this.nameid = `${this._sex?this._sex:''}_${this._name}_${this._dob}`
    }

    static defaultPerson(){
        return new Person('Pg','12/12/1988','M')
    }
}

console.log(`constructor :${new Person('Pradhap','11/11/1988','Male')}`)
console.log(`static : ${Person.defaultPerson()}`)

let p2 = new Person('Ganesan');
console.log(p2.min_req);