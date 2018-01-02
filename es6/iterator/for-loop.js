
// for..of iterates array/collections and gives VALUES
function forOf(arr){
      console.log('------ for..of iterates array/collections and gives VALUES')
      for(v of arr){
            console.log(v)
      }
}
// for..in iterates and gives INDEX and values accessible
function forIn(arr){
      console.log('------ for..in iterates and gives INDEX')
      for(i in arr){
            console.log(i)
      }
}

// forEach declarative way to iterate gives VALES
function forEachApi(arr){
      console.log('----- forEach declarative way to iterate gives VALES')
      arr.forEach(v=>console.log(v))
}

//legacy way of iteration gives INDEX and VALUES
function forLegacy(arr){
      console.log('------- legacy way of iteration')
      for(i=0; i<arr.length; i++){
            console.log(`index : ${i} , val : ${arr[i]}`)
      }
}

let mixType = [1, 2.4, true, null, "adf"]

forOf( mixType )
forIn ( mixType)
forEachApi (mixType)
forLegacy(mixType)
