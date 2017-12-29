let isSuccess;
let prom1 = new Promise(function (res, rej) {
    if (isSuccess) {
        res('Res:');
    }else{
        throw new Error('Undefinded')
    }
})

prom1.then(function (res) {
        console.log(res + ' Long wait call RESPONDED')})
      .catch(function(err){
        console.log(err+" Exception caught")})