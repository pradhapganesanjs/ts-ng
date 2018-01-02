/**
 * Generator a special function with*
 * Generator comprises Iterator (data producers) and Observer (data consumers);
 * Iterator.next() - gives data 
 * yield (data consumer) - pause execution and keep observes next() to be called to receive data.
 * 
 * yield vs return 
 * yield : pauses execution and wait until next() is called; take value from previous and pass to next;
 * return : just return executed vals;
 * 
 * Pros:
 *  Best at replace complex Promise. 
 *   Ex-Promise: function fetchJson(url) { return fetch(url) .then(...) .then(text => {...})
 *   Ex-Generat: function* fetchJson(url) { let request = yield fetch(url); let text = yield request.text(); return JSON.parse(text);
 * 
 */

 function* genAlpha(){
    console.log('started') 
    yield 'A'
    yield 'B'
    console.log('END')
 }
 for(a of genAlpha()) { console.log(a) }