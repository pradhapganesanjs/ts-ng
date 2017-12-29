
/*
* in JS, classes are not hoisted; 
* because a class may extend other class and that would lead lot other depencies.
*/

new Foo(); // ReferenceError: Foo is not defined
class Foo {}

//---------------------------------------------------------------------
/**
 * Functions are Hoisted; NOT when class reference involved
 * But if the function create instance of a class then it can not be hoisted.
 */
function functionThatUsesBar() {
    new Bar(); //ReferenceError: Bar is not defined
}
functionThatUsesBar(); // ReferenceError
class Bar {}