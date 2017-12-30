/**
 * 
 * 
 */

const arrConst = [1,2,3,4,5]
      printArr ( arrConst );

//Alter const array test      
       arrConst.push(22);
       let arrFiltered = arrConst.filter(i=>i>10);
       printArr(arrFiltered);


    function printArr([...arr]){
        arr.forEach(i=>console.log(i));
      }