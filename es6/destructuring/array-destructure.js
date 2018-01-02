
let arrInt = [1,2,3,4,5,6]
let firstHalf = arrInt.slice(3)
let lastHalf = arrInt.slice(3,arrInt.length)
console.log(firstHalf+ ' '+lastHalf)

//destructured array assignment
let [st,nd,th,...lHalf] = arrInt
console.log(`${st},${nd},${th}`+ ' '+lHalf)

var list = [ 7, 42 ]
var [ a = 1, b = 2, c = 3, d ] = list
console.log(`a : ${a}`)// 7
console.log(`b : ${b}`)// 42
console.log(`c : ${c}`)// 3
console.log(`d : ${d}`)// undefined
