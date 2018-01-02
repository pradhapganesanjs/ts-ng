let arrInt = [11,22,33,44]
let arrIntIter = arrInt[Symbol.iterator]()
let nxt = arrIntIter.next();
while(!nxt.done){
    console.log(nxt.value)
    nxt = arrIntIter.next();
}
