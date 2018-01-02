let mixType = [1, 2.4, true, null, "adf"]

arrParamFunc( mixType )

function arrParamFunc([... params]){
      for(v of params){
            console.log(v)
      }
}