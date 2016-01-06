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
		login: function(username, password, success, done) {
			password = stringUtil.md5(password);
			$.ajax({
				type: "POST",
				url: basePath + userPath + "/login",
				showLoading: true,
				data: {
					username: username,
					password: password
				}
			}).done(function(result, status, jqXHR) {
				if (result.header.success) {
					var user = result.body;
					window.sessionStorage.setItem(App.Constants.SESSION_USER_KEY, JSON.stringify(user));
				}
				success(result);
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
				}
			}).done(success);
		},
		getUser: function(userId, success) {
			$.ajax({
				type: "GET",
				url: basePath + userPath + "/" + userId
			}).done(success);
		},
		addUser: function(user, success) {
			$.ajax({
				type: "POST",
				showSuccessMsg: false,
				url: basePath + userPath + "/",
				data: JSON.stringify(user),
				contentType: 'application/json;charset=utf-8'
			}).done(success);
		},
		updateUser: function(user, success) {
			$.ajax({
				type: "PUT",
				showSuccessMsg: false,
				url: basePath + userPath + "/" + user.id,
				data: JSON.stringify(user),
				contentType: 'application/json;charset=utf-8'
			}).done(success);
		},
		saveOrUpdateUser: function(user, success) {
			if (user.id) {
				this.updateUser(user, success);
			} else {
				this.addUser(user, success);
			}
		},
		deleteUser: function(userId, success) {
			$.ajax({
				type: "DELETE",
				showSuccessMsg: true,
				url: basePath + userPath + "/" + userId
			}).done(success);
		},
		organizationTree: function(success, showLoading) {
			$.ajax({
				type: "GET",
				showLoading: showLoading,
				url: basePath + organizationPath + "/tree"
			}).done(success);
		},
		organizationList: function(success) {
			$.ajax({
				type: "GET",
				url: basePath + organizationPath + "/list"
			}).done(success);
		},
		getOrganization: function(organizationId, success) {
			$.ajax({
				type: "GET",
				url: basePath + organizationPath + "/" + organizationId
			}).done(success);
		},
		addOrganization: function(organization, success) {
			$.ajax({
				type: "POST",
				url: basePath + organizationPath + "/",
				data: JSON.stringify(organization),
				showSuccessMsg: false,
				contentType: 'application/json;charset=utf-8'
			}).done(success);
		},
		updateOrganization: function(organization, success) {
			$.ajax({
				type: "PUT",
				url: basePath + organizationPath + "/" + organization.id,
				data: JSON.stringify(organization),
				showSuccessMsg: true,
				contentType: 'application/json;charset=utf-8'
			}).done(success);
		},
		saveOrUpdateOrganization: function(organization, success) {
			if (organization.id) {
				this.updateOrganization(organization, success);
			} else {
				this.addOrganization(organization, success);
			}
		},
		deleteOrganization: function(organizationId, success) {
			$.ajax({
				type: "DELETE",
				showSuccessMsg: true,
				url: basePath + organizationPath + "/" + organizationId
			}).done(success);
		},
		organizationUsers: function(organizationId, success) {
			$.ajax({
				type: "GET",
				url: basePath + organizationPath + "/" + organizationId + '/users'
			}).done(success);
		}
	};
	App.Services.User.prototype.constructor = App.Services.User;
	return new App.Services.User();
});