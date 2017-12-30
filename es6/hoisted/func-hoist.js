
functionAreHoisted();

function functionAreHoisted(){
    console.log("Yes functions are hoisted in JS");
}

//-- function will NOT be hoisted other than legacy way of definition

//NOT - Hoisted - ReferenceError: printArrowFun is not defined
printArrowFun(["<",">",">>","<<"])
    printArrowFun = arr => arr.forEach( i => console.log(i))


//NOT - Hoisted - ReferenceError: printArr is not defined
printArr(["a","b","c","d"]);
    printArr = function(arr){
        for(i in arr){
            console.log(arr[i]);
        }
    };

