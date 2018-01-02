sumArr = (arr) => {
    let sum = 0;
    for(v of arr){
        sum += v;
    }
    return sum;
}
console.log(sumArr([1,2,3,5]))