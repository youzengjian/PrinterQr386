var exec = require('cordova/exec');

exports.getPariedDevices = function (success, error) {
    exec(success, error, 'PrinterQr386', 'getPariedDevices', []);
};

exports.connectPrinter = function (printer_name, printer_addr, success, error) {
    exec(success, error, 'PrinterQr386', 'connectPrinter', [printer_name, printer_addr]);
};