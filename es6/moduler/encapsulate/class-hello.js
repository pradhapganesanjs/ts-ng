class Person{
    constructor(name, dob, sex){
        this.name = name;
        this.dob = dob;
        this.sex = sex;
    }
    getPerson(){
        return this;
    }
}

const prad = new Person("Pradhap", "12-12-1988","M","dum");
console.log(prad.getPerson());