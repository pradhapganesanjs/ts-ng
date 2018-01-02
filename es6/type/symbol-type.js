/**
 * symbols to avoid name clashes in property keys. 
 * symbols as global as globals get in JavaScript
 * Symbol.for(key) and Symbol.keyFor(symbol)
 */

Symbol.for("app.foo") === Symbol.for("app.foo")
const foo = Symbol.for("app.foo")
const bar = Symbol.for("app.bar")
Symbol.keyFor(foo) === "app.foo"
Symbol.keyFor(bar) === "app.bar"
