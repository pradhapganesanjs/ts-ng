/**
 * 
 * Scope of a variable or a function is close to its block
 * 
 */

//----- ES6 ---
{
    function foo () { return 1 }
    console.log("ES6-1) foo() === 1 "+foo()+" "+ (foo() === 1));
    {
        function foo () { return 2 }
        console.log("ES6-0) foo() === 2 "+foo()+" "+ (foo() === 2));
    }
    console.log("ES6-1) foo() === 1 "+foo()+" " + (foo() === 1));
}


//--- ES5 implementation
(function () {
    var foo = function () { return 1; }
    console.log("1) foo() === 1 " + (foo() === 1));
    (function () {
        var foo = function () { return 2; }
        console.log("foo() === 2 " + (foo() === 2));
    })();
    console.log("2 foo() === 1 "+ (foo() === 1));
})();