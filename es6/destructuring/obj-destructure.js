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

let {name, dob, phone} = customer;
console.log(`${name} ${dob} ${phone} `) //o/p : Pradhap Ganesan 11/11/1988 2013334444

//alias name give object properties
let {street1 : primAddSt1, street2 : primAddApt, city : primAddCity} = customer.paddress
console.log(`${primAddSt1} ${primAddApt} ${primAddCity} `) //o/p: 9100 NW 52.. Apt 222 Miami

//object can be destructured one level at a time
let {paddress:address1, caddress:address2} = customer
let {city : city1} = address1
let {city : city2} = address2
console.log(`${city1} , ${city2}`)

//let {name=n, dob=d, phone=min} = customer;
/* ==>
Short expression for below lines is " let {name, dob, phone} = customer;   "
name = customer.name
dob = customer.dob
phone = customer.phone
*/
