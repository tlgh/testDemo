define(function () {
    var App = {
        Models: {},
        Views: {},
        Services: {},
        Utils: {},
        Constants: {},
        initialize: function () {
            App.Constants.SESSION_USER_KEY = 'currentLoginUser';
            App.Constants.BASE_PATH = 'http://127.0.0.1:8080/dcim-modeling-server/api';
            var html = window.location.href.toString();
            /*var paramsIndex =  html.indexOf('?');
            if (paramsIndex > 0) {
				html = html.substr(0 , paramsIndex);
			}*/
            var ctrl = html.replace('.html', '.js');
            require([ctrl]);
        }
    };
    return App;
});

