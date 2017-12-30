/**
 * let declaration applies bellow rules:
 * >> allow to reassign values
 * >> restrict from same variable being declared
 * >> restrict the variable scope within defined block
 */

 //* >> allow to reassign values
 let count = 1;
 const EN = "ENGLISH"
 for(c of EN){
     count+=1;
 }
 console.log(`length of ${EN} is ${count}`)
 
 let checkPeriod = guessPeriod(10);
 
 
 function guessPeriod(t){
    switch(t){
        case (t>3 && t<=11):
            return 'morning';
        case (t>11 && t<=16):
            return 'noon';    
        case (t>16 && t<=20):
            return 'evening';    
        case (t<=3 || t>20):
            return 'night';            
    }
 }
 