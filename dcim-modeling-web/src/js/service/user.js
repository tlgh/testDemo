/**
 * Created by boil on 2015-12-14.
 */
define([
	"main",
	"util/string",
	"util/array"
], function(App, stringUtil, arrayUtil) {

	var basePath;
	var organizationPath = '/organization';
	var userPath = '/user';

	App.Services.User = function() {
		basePath = App.Constants.BASE_PATH;
	};

	App.Services.User.prototype = {
		getCurrentUser: function() {
			var user = window.sessionStorage.getItem(App.Constants.SESSION_USER_KEY);
			if (user) {
				try {
					return JSON.parse(user);
				} catch (e) {
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
				resultHandler: function(result) {
					if (result.header.success) {
						var user = result.body;
						window.sessionStorage.setItem(App.Constants.SESSION_USER_KEY, JSON.stringify(user));
					}
					success(result);
				}
			});
		},
		logoff: function() {
			window.sessionStorage.removeItem(App.Constants.SESSION_USER_KEY);
		},
		userPage: function(organizationId, name, success) {
			$.ajax({
				type: "GET",
				url: basePath + userPath + "/list",
				data: {
					organizationId: organizationId,
					name: name
				},
				resultHandler: success
			});
		},
		addUser: function(user, success) {
			$.ajax({
				type: "POST",
				showSuccessMsg: false,
				url: basePath + userPath + "/",
				data: JSON.stringify(user),
				contentType: 'application/json;charset=utf-8',
				resultHandler: success
			});
		},
		updateUser: function(user, success) {
			$.ajax({
				type: "PUT",
				showSuccessMsg: false,
				url: basePath + userPath + "/" + user.id,
				data: JSON.stringify(user),
				contentType: 'application/json;charset=utf-8',
				resultHandler: success
			});
		},
		deleteUser: function(userId, success) {
			$.ajax({
				type: "DELETE",
				showSuccessMsg: true,
				url: basePath + userPath + "/" + userId,
				data: JSON.stringify(user),
				contentType: 'application/json;charset=utf-8',
				resultHandler: success
			});
		},
		organizationTree: function(success) {
			$.ajax({
				type: "GET",
				url: basePath + organizationPath + "/tree",
				resultHandler: success
			});
		},
		organizationList: function(success) {
			$.ajax({
				type: "GET",
				url: basePath + organizationPath + "/list",
				resultHandler: success
			});
		},
		addOrganization: function(organization, success) {
			$.ajax({
				type: "POST",
				url: basePath + organizationPath + "/",
				data: JSON.stringify(organization),
				showSuccessMsg: false,
				contentType: 'application/json;charset=utf-8',
				resultHandler: success
			});
		},
		updateOrganization: function(organization, success) {
			$.ajax({
				type: "PUT",
				url: basePath + organizationPath + "/" + organization.id,
				data: JSON.stringify(organization),
				showSuccessMsg: true,
				contentType: 'application/json;charset=utf-8',
				resultHandler: success
			});
		},
		deleteOrganization: function(organizationId, success) {
			$.ajax({
				type: "DELETE",
				showSuccessMsg: true,
				url: basePath + organizationPath + "/" + organizationId,
				resultHandler: success
			});
		},
		organizationUsers: function(organizationId, success) {
			$.ajax({
				type: "GET",
				url: basePath + organizationPath + "/" + organizationId + '/users',
				resultHandler: success
			});
		}
	};
	App.Services.User.prototype.constructor = App.Services.User;
	return new App.Services.User();
});