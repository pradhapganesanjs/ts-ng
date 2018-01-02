let arr = [11,22,33,44,55,66]

for(i of someLogic(arr)){
    console.log(i)
}
function* someLogic(arr){
    for(i of arr){
        if(i%2==0)
            yield i;
    }
}