var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'PrinterQr386', 'coolMethod', [arg0]);
};
