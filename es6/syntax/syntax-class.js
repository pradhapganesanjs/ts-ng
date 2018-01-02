
class ClassPropGetSet{
    constructor(prop1, prop2){
        this._prop1 = prop1;
        this._prop2 = prop2;
    }
    get _prop1(){
        return this._prop1;
    }
    get _prop2(){
        return this._prop2;
    }

    set _prop1(prop){
        this._prop1 = prop;
    }

    set _prop2(prop){
        this._prop2 = prop;
    }    
}

cprop = new ClassPropGetSet("description","shortDesc")
console.log(`${cprop._prop1}, ${cprop._prop2}`)
cprop._prop1("updatedDesc")
cprop._prop2("updatedShortDesc")
console.log(`${cprop._prop1}, ${cprop._prop2}`)