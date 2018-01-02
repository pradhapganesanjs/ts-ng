/**
 * ... spread operator (var arg in java)
 * 
 */

iterStrParam = ( [...strArr ] ) => strArr.forEach(s=>console.log(s));
iterStrParam(["gp","pg","xy","zz"]); //array parameters are passed to rest type argument