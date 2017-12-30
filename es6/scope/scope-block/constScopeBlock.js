/**
 * const key word can be used on declaring variables.
 * constant can be defined/assigned only once; can not reassign;
 * 
 */

const PI = 3.17

if(true){
    const PI = 2.22;
    console.log(`const variable can be reassigned within lower block ${PI}`)
}

console.log(`const variable can NOT be reassigned within block ${PI}`)