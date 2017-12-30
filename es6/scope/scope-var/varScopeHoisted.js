/**
 * var variables are hoisted, because of this it can be accessible outside block
 */

for(var i =0; i < 5; i++){
    if(i==4){
        var x = 10;
    }
}
console.log("outside block i : "+i); //output:5
console.log("outside block x : "+x); //output:10

//---- Same is not possible with 'let'