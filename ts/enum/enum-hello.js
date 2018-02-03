var STR_ENUM;
(function (STR_ENUM) {
    STR_ENUM["A"] = "A ONE";
    STR_ENUM["B"] = "B TWO";
})(STR_ENUM || (STR_ENUM = {}));
console.log(STR_ENUM.A);
console.log(STR_ENUM.A.toString());
console.log(STR_ENUM.A.valueOf());
