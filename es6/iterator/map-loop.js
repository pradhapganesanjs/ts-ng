let map = new Map();
map.set("one","1");
map.set("two","2");
map.set("three","3");
map.set("four","4");

for(let [k, v] of map.entries()){
    console.log(`key : ${k}, val: ${v}`);
}