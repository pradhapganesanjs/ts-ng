
//simple expression
console.log(`10 + 34 = ${ 10 +34 }`)

//func call in template
sumArr = ([...nums]) => {
    console.log(`sum of array ${nums.reduce((prev,curr) => prev+curr)}`)
}
sumArr([1,2,3,4,5]);


