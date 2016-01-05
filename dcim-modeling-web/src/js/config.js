/**
 * Created by boil on 2015-12-15.
 */
requirejs.config({
	//By default load any module IDs from js/lib
	//except, if the module ID starts with "app",
	//load it from the js/app directory. paths
	//config is relative to the baseUrl, and
	//never includes a ".js" extension since
	//the paths config could be for a directory.
	shim: {
		'sweetAlert': {
			//These script dependencies should be loaded before loading
			deps: ['css!vendor/sweet-alert/sweet-alert.css']
		},
		'ZY': {
			//These script dependencies should be loaded before loading
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
require([
	'jquery',
	'main'
], function(jquery, app) {
	app.initialize();
});