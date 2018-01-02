function* genFib(end){
    let pre=0, cur=1
    while(end-- > 0){
        let tcur = cur 
        cur = pre+cur 
        pre = tcur 
        yield cur
    }
}

for(v of genFib(10)){
    console.log(v)
}