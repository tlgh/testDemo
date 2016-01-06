define(function(require) {

	// Import depdendencies (note you can use relative paths here)
	var stringUtil = require("util/string");

	var src = '123456';
	var code = 'e10adc3949ba59abbe56e057f20f883e';

	// Define the QUnit module and lifecycle.
	QUnit.module("util/string");

	QUnit.test("MD5 encrypt test", function(assert) {
		assert.ok(stringUtil.md5(src) == code, "encrypt sucess");
	});

});