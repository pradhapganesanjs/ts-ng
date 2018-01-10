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
var mapdd = new Map();
mapdd.set('hello2', 'world2');
for (var _i = 0, _a = Array.from(mapdd.keys()); _i < _a.length; _i++) {
    var key = _a[_i];
    console.log(key + ' , ' + mapdd.get(key));
}
