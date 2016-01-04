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
			'css': 'require-css/css' // or whatever the path to require-css is
		}
	}
});
require([
	'jquery',
	'main'
], function(jquery, app) {
	app.initialize();
});