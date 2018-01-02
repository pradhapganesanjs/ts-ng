/**
 * const key word can be used on declaring variables.
 * constant can be defined/assigned only once; can not reassign;
 * 
 */

const PI = 3.14
      PI = 2.22 //TypeError: Assignment to constant variable.
console.log(PI)

/**
 * const array/object internal values CAN be changed/modified. 
 * Only const variable can NOT be reasigned.
 */
const arrConst = [1,2,3,4,5]
      printArr ( arrConst );
       arrConst.push(22);

       const arrConst = [1,2,3,4,5]
       printArr ( arrConst );
 
 //Alter const array test      
        arrConst.push(22);
        let arrFiltered = arrConst.filter(i=>i>10);
        printArr(arrFiltered);
 
 
     function printArr([...arr]){
         arr.forEach(i=>console.log(i));
       }
