
//parameters in function default values(when params are not passed) can be assgined in place; 
getFName = (fName='N/A', lName='N/A') => `${fName} ${lName}`
console.log(getFName('Pradhap')) //Pradhap N/A
console.log('-----------------------------------------------')
function funParamDefaults(address = {street:"2100 Citi Center",zip:"33610"},name="n/a"){
    this._name = name;
    this._address={};
    this._address.street = address.street;
    this._address.zip = address.zip;}

let prad = new funParamDefaults(name="pradhap");
console.log(prad)

//parameter alias names can be assinged in place
function funParamAlias(){

}