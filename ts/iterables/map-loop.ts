// ()=>{
//     let mapt: Map<string, string> = new Map<string, string>();
//     mapt.set("one","1");
//     mapt.set("two","2");
//     mapt.set("three","3");
//     mapt.set("four","4");

//     for(let k of Array.from(mapt.keys())){
//         console.log(`key : ${k}, val: ${mapt.get(k)}`);
//     }
// };

let mapdd: Map<string, string> = new Map<string, string>();
mapdd.set('hello2', 'world2');

for (let key of Array.from(mapdd.keys())) {
    console.log(key+' , '+mapdd.get(key));
}