
/**
 * function call doCalc `....` (doCalc<space><parameters>) is differ from doCalc('....')
 * @param {*} arithm 
 * @param {*} values 
 */
function doCalc(arithm, ...values){
    if('add' == arithm[0]){
        console.log(`${values[0]} + ${values[1]} = ${values[0] + values[1]} `)
    }else if('sub' == arithm[0]){
        console.log(`${values[0]} - ${values[1]} = ${values[0] - values[1]} `)
    }else{
        console.log(`Operation not supported`)
    }
}
let l = 100;
let s =50;
doCalc `add${l}${s}`
