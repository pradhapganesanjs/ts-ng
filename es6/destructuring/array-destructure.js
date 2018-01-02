
let arrInt = [1,2,3,4,5,6]
let firstHalf = arrInt.slice(3)
let lastHalf = arrInt.slice(3,arrInt.length)
console.log(firstHalf+ ' '+lastHalf)

//destructured array assignment
let [st,nd,th,...lHalf] = arrInt
console.log(`${st},${nd},${th}`+ ' '+lHalf)
