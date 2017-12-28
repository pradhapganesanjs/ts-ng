/**
 * let key word restrict variable scope to block scope
 */
var callBacks=[];
for(let i =0 ; i< 5; i++){
    callBacks[i] = function(i){
        switch(i*2){
            case 0: return 0;
            case 1: return 10;
            case 2: return 25;
            case 3: return 38;
            case 4: return 41;
            default : return -1;            
        }
    };
}

console.log(' ' + callBacks[1](0));