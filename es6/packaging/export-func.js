"use strict";
exports.__esModule = true;
function toggleCase(str) {
    var retStr = '';
    for (var i = 0; i < str.length; i++) {
        if (str.charCodeAt(i) > 96) {
            retStr = retStr + str[i].toUpperCase();
        }
        else {
            retStr = retStr + str[i].toLowerCase();
        }
    }
    return retStr;
}
exports.toggleCase = toggleCase;
