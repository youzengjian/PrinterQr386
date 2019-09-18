var exec = require('cordova/exec');

exports.getPariedDevices = function (success, error) {
    exec(success, error, 'PrinterQr386', 'getPariedDevices', []);
};

exports.connect = function (printer_name, printer_addr, success, error) {
    exec(success, error, 'PrinterQr386', 'connect', [printer_name, printer_addr]);
};

exports.disconnect = function (success, error) {
    exec(success, error, 'PrinterQr386', 'disconnect', []);
};

exports.isConnected = function (success, error) {
    exec(success, error, 'PrinterQr386', 'isConnected', []);
};

exports.print = function (horizontal, skip, success, error) {
    exec(success, error, 'PrinterQr386', 'print', [horizontal, skip]);
};

exports.pageSetup = function (pageWidth, pageHeight, success, error) {
    exec(success, error, 'PrinterQr386', 'pageSetup', [pageWidth, pageHeight])
}

exports.drawText = function (text_x, text_y, text, fontSize, rotate, bold, reverse, underline, success, error) {
    exec(success, error, 'PrinterQr386', 'pageSetup', [text_x, text_y, text, fontSize, rotate, bold, reverse, underline])
}