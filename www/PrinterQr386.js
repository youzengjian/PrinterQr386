var exec = require('cordova/exec');

exports.getPariedDevices = function (arg0, success, error) {
    exec(success, error, 'PrinterQr386', 'getPariedDevices', [arg0]);
};
