(function() {
	requirejs.config({

		baseUrl: "../src/js",
		shim: {
			'sweetAlert': {
				deps: ['css!vendor/sweet-alert/sweet-alert.css']
			},
			'ZY': {
				deps: ['css!vendor/zy-ui/widgets.css']
			}
		},
		paths: {
			jquery: 'vendor/jquery.min',
			main: 'main',
			model: 'model',
			service: 'service',
			util: 'util',
			ZY: 'vendor/zy-ui/ZY.UIlib',
			sweetAlert: 'vendor/sweet-alert/sweet-alert'
		},
		map: {
			'*': {
				'css': 'vendor/css.min' // or whatever the path to require-css is
			}
		}
	});

	var testModules = [
		"service/UserTests.js",
		"util/StringTests.js"
	];

	require([
		'jquery',
		'main'
	], function(jquery, app) {

		initConfig(app);
		// Resolve all testModules
		require(testModules, function(App) {
			/*QUnit.load();
			QUnit.start();*/
		});
	});

	function initConfig(App) {
		App.Constants.SESSION_USER_KEY = 'currentLoginUser';
		App.Constants.BASE_PATH = 'http://127.0.0.1:8080/dcim-modeling-server/api';
	}

}());