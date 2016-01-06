define(function(require) {

	// Import depdendencies (note you can use relative paths here)
	var userService = require("service/user");

	var username = 'admin';
	var password = '123456';
	var userId, orgId;
	var USER = {
		"organization": {
			"id": "orgRoot"
		},
		"name": "测试用户",
		"sex": "1",
		"username": "ceshiyonghu",
		"email": "ceshiyonghu@qq.com",
		"mobile": "13999999999",
		"birthday": "2000-10-10 00:00:00"
	};

	var ORG = {
		"parent": {
			"id": "orgRoot"
		},
		"name": "测试机构@&",
		"description": "测试机构！！！"
	};

	// Define the QUnit module and lifecycle.
	QUnit.module("service/user");

	QUnit.test("Login test", function(assert) {
		var done = assert.async();
		userService.login(username, password, function(result) {
			assert.ok(result.header.success, "login success");
			var user = userService.getCurrentUser();
			assert.ok(user.username == username, "get currentUser success");
			done();
		});
	});

	QUnit.test("Logoff test", function(assert) {
		userService.logoff();
		var user = userService.getCurrentUser();
		assert.ok(user == null, "logoff success");
	});

	QUnit.test("Delete Organization", function(assert) {
		var done = assert.async();
		userService.deleteOrganization(orgId, function(result) {
			assert.ok(result.header.success, "delete organization success");
			userService.getOrganization(orgId, function(result) {
				assert.ok(result.body == null, "check organization exist success");
				done();
			});
		});
	});

	QUnit.test("Delete User", function(assert) {
		var done = assert.async();
		userService.deleteUser(userId, function(result) {
			assert.ok(result.header.success, "delete user success");
			userService.getUser(userId, function(result) {
				assert.ok(result.body == null, "check user exist success");
				done();
			});
		});
	});



	QUnit.test("Update User", function(assert) {
		var user = USER;
		user.name = user.name + 1;
		var done = assert.async();
		userService.updateUser(user, function(result) {
			assert.ok(result.header.success, "update user success");
			userService.getUser(userId, function(result) {
				assert.ok(result.body.name == user.name, "check user update info success: " + result.body.name + "==" + user.name);
				done();
			});
		});
	});

	QUnit.test("Get User", function(assert) {
		var done = assert.async();
		userService.getUser(userId, function(result) {
			assert.ok(result.header.success, "get user success");
			assert.ok(result.body != null, "check user info success :" + result.body.name);
			done();
		});
	});

	QUnit.test("Organization Users", function(assert) {
		var done = assert.async();
		userService.organizationUsers(orgId, function(result) {
			assert.ok(result.header.success, "organization users success");
			var existing = false;
			$.each(result.body, function() {
				if (this.id == userId) {
					existing = true;
				}
			});
			assert.ok(existing, "check organization users info success :" + ORG.name + '->' + USER.name);
			done();
		}, true);
	});

	QUnit.test("Add User", function(assert) {
		var user = USER;
		user.organization.id = orgId;
		var done = assert.async();
		userService.addUser(user, function(result) {
			assert.ok(result.header.success, "add user success");
			userId = result.body;
			user.id = userId;
			done();
		});
	});


	QUnit.test("Organization Tree", function(assert) {
		var done = assert.async();
		userService.organizationTree(function(result) {
			assert.ok(result.header.success, "organizationTree success");
			var existing = false;
			var root = result.body[0];
			$.each(root.children, function() {
				if (this.id == orgId) {
					existing = true;
				}
			});
			assert.ok(existing, "check organization info success :" + ORG.name);
			done();
		}, true);
	});

	QUnit.test("Update Organization", function(assert) {
		var organization = ORG;
		organization.name = organization.name + 1;
		var done = assert.async();
		userService.updateOrganization(organization, function(result) {
			assert.ok(result.header.success, "update organization success");
			userService.getOrganization(orgId, function(result) {
				assert.ok(result.body.name == organization.name, "check organization update info success: " + result.body.name + "==" + organization.name);
				done();
			});
		});
	});


	QUnit.test("Get Organization", function(assert) {
		var done = assert.async();
		userService.getOrganization(orgId, function(result) {
			assert.ok(result.header.success, "get organization success");
			assert.ok(result.body != null, "check organization info success :" + result.body.name);
			done();
		});
	});

	QUnit.test("Add Organization", function(assert) {
		var organization = ORG;
		var done = assert.async();
		userService.addOrganization(organization, function(result) {
			assert.ok(result.header.success, "add organization success");
			orgId = result.body;
			organization.id = orgId;
			done();
		});
	});



});