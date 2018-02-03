let gcolor = function(color){
    return function(target){
        target.l = color;
    }
}

@gcolor ('pink')
class G {

}
console.log(`g color is ${G.l}`);
const gi1 = new G();
console.log(`gi1 color is ${gi1.l}`);

function print(target){
    target.fullName = 'prad gane';
}


@print
class Person{

 private _fname;
 private _lname;

 constructor(fn, ln){
     this._fname = fn;
     this._lname = ln;
 }

 get fname(){
     return this._fname;
 }
 get lname(){
     return this._lname;
 }

 set fname(fn){
     this._fname = fn;
 }
 set lname(ln){
     this._lname = ln;
 }
}

const pers = new Person('pradhap','ganesan');
console.log(' fname '+Person.fullName)
