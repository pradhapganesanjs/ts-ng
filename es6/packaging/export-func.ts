export function toggleCase(str){
        let retStr='';
        for(let i =0; i<str.length;i++){
            if(str.charCodeAt(i)>96){
                retStr = retStr+str[i].toUpperCase();
            }else{
                retStr = retStr+str[i].toLowerCase();
            }
        }
        return retStr;
    }

