function Animal(name, isWild){
    return {
        name,
        isWild,
        getInfo(){
            return `${this.name} ${this.isWild}`;
        },
        owner : {
            ownerName : "Will",
            ownerCountry : "USA"
        }
    }
}
 
let dog = new Animal("dog","No");

console.log(dog.getInfo())
console.log(dog.owner.ownerCountry)