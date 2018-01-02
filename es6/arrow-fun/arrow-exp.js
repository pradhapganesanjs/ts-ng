let even = [0,2,4,6,8];

for(e in even){
    console.log(even[e])
}

var newEven = even.map(n => n/2);
for(e in newEven){   console.log(newEven[e])  }

var newEvenPair = even.map( n => ({before:n, after:n/2}))
for(e in newEvenPair){ console.log(newEvenPair[e])}