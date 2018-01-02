class Student{
    constructor(name){
        this._name = name;
    }
    get name(){
        console.log('getter called')
        return this._name;
    }
    set name(name){
        console.log('setter called')
        this._name = name;
    }
    set dep(department){
        this._dep = department;
    }
    getDtl(){
        return `Name: ${this._name} Department: ${this._dep?this._dep:'not assigned'}`;
    }
}

let prad = new Student()
prad.name = "Pradhap"
prad.dep = "IT"
console.log(prad.name)
console.log(prad.getDtl())