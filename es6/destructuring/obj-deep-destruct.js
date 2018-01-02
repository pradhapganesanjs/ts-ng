let customer = {
    name : "Pradhap Ganesan",
    dob : "11/11/1988",
    ssn :"2211",
    phone:2013334444,
    paddress :{
        street1: "9100 NW 52..",
        street2 : "Apt 222",
        city:"Miami"
    },
    caddress :{
        street1:"....",
        city:"Brandon"
    }
}

//object can be destructured one level at a time
let {paddress:address1, caddress:address2} = customer
let {paddress:{city:pcity}, caddress:{city:ccity}} = customer
console.log(`${pcity} , ${ccity}`)
