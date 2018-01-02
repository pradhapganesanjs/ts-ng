/**
 * 
 * 
 */

let numArr = [1,2,3,4,5]
let strArr = ["a","b"]
let mixType = [1, 2.4, true, null, "adf"]

arrParamFunc( mixType )


function arrParamFunc([... params]){
      for(v of params){
            console.log(v)
      }
}
