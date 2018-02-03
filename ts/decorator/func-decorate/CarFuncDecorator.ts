// let funcreadonly = function (target,key,descriptor){
//     descriptor.writable=false;
//     return descriptor;
// }

//const descriptor = funcreadonly(target.prototype,key,descriptor);
//Object.defineProperty(target.prototype,key,descriptor) || descriptor;

let funcreadonly = function (target,key,descriptor){
    console.log('target : '+target);
    console.log('key : '+key);
    //descriptor = funcreadonly(target.prototype,key,descriptor);
    const desc = Object.defineProperty(target,key,{writable:false});
    console.log('descriptor '+desc);
    //desc.writable = false;
    return desc;
}

class Car{

    model = "Basic";
    @funcreadonly
    updateModel(){
        return this.model;
    }
}

// const c1 = new Car();
// c1.updateModel("Sports");
// console.log('c1 '+c1.model);
 Car.prototype.updateModel = function(x){
     this.model="car model is HACKED...";
 }
const c12 = new Car();
c12.updateModel("Sports");
console.log('c12 '+c12.model);
