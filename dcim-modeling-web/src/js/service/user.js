/**
 * Created by boil on 2015-12-14.
 */
define([
    "main",
    "util/string",
    "util/array"
], function (App, stringUtil, arrayUtil) {
	
	var basePath;
	var organizationPath = '/organization';
	var userPath = '/user';
	
    App.Services.User = function () {
		basePath = App.Constants.BASE_PATH;
    };

    App.Services.User.prototype = {
    	getCurrentUser: function(){
    		var user = window.sessionStorage.getItem(App.Constants.SESSION_USER_KEY);
		    if (user) {
		    	try{
		        	return JSON.parse(user);
		    	}catch(e){
		    		e.print();
		    	}
		    }
    	},
        login: function(username, password, success) {
			password = stringUtil.md5(password);
			$.ajax({
				type: "POST",
				url: basePath + userPath + "/login",
				data: {
					username: username,
					password: password
				},
                dataType: 'json',
				resultHandler: function(result){
					if (result.header.success) {
						var user = result.body;
						window.sessionStorage.setItem(App.Constants.SESSION_USER_KEY, JSON.stringify(user));
					}
					success(result);
				}
			});
		},
		logoff: function(){
			window.sessionStorage.removeItem(App.Constants.SESSION_USER_KEY);
		},
		userPage: function (organizationId, name, success) {
            $.ajax({
				type: "GET",
				url: basePath + userPath + "/list",
				data: {
					organizationId: organizationId,
					name: name
				},
				dataType: "json",
				resultHandler: success
			});
        },
		addUser: function(user, success) {
			$.ajax({
				type: "POST",
				url: basePath + userPath + "/",
				data: JSON.stringify(user),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
				resultHandler: success
			});
		},
		organizationTree: function(success) {
			$.ajax({
				type: "GET",
				url: basePath + organizationPath + "/tree",
				dataType: "json",
				resultHandler: success
			});
		},
		organizationList: function(success) {
			$.ajax({
				type: "GET",
				url: basePath + organizationPath + "/list",
				dataType: "json",
				resultHandler: success
			});
		},
		addOrganization: function(organization, success) {
			$.ajax({
				type: "POST",
				url: basePath + organizationPath + "/",
				data: JSON.stringify(organization),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
				resultHandler: success
			});
		},
		organizationUsers: function(organizationId, success) {
			$.ajax({
				type: "GET",
				url: basePath + organizationPath + "/" + organizationId + '/users',
				dataType: "json",
				resultHandler: success
			});
		}
    };
    App.Services.User.prototype.constructor = App.Services.User;
    return new App.Services.User();
});
