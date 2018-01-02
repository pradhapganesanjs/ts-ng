
function* genInt(){
    let i = 5
    while(i>0){
        yield i--
    }
}

let [i,j,k,l] = genInt()
console.log(`${i},${j},${k},${l}`)

let arr = [...genInt()]
console.log(arr)

for(v of genInt()){
    console.log(v)
}