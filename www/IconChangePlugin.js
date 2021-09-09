var exec = require('cordova/exec');


exports.checkSupport = function (success, error) {
    exec(success, error, 'IconChangePlugin', 'checkSupport');
};


exports.changeIcon = function (param, success, error) {
    exec(success, error, 'IconChangePlugin', 'changeIcon', [param]);
};
