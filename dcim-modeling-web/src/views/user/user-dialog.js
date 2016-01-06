/**
 * Created by boil on 2015-12-14.
 */
"use strict"
define([
	'service/user',
	'util/array',
	'util/url',
	'ZY'
], function(userService, arrayUtil, urlUtil) {
	var selectOrganization = $('#selectOrganization');
	var form = new ZY.UI.Form("userForm");

	//init datas
	var user = {};
	var userId = urlUtil.getUrlParameter('userId');
	var organizationId = urlUtil.getUrlParameter('organizationId');
	if (userId) {
		userService.getUser(userId, function(result) {
			if (result.header.success) {
				user = result.body;
				form.setValue(user);
			}
		});
	} else {
		form.setValue(user);
	}

	userService.organizationTree(function(result) {
		selectOrganization.empty();

		arrayUtil.traverseTree(result.body, function(child, level) {
			var name = child.name;
			for (var i = 0; i < level; i++) {
				name = '　' + name;
			}
			var option = $('<option>');
			option.text(name);
			option.val(child.id);
			selectOrganization.append(option);
			if (organizationId) {
				selectOrganization.val(organizationId);
			}
		});
	});

	//register events
	$('#userForm').submit(function() {
		var user = form.getValue();
		user.birthday = user.birthday + ' 00:00:00';
		userService.saveOrUpdateUser(user, function(result) {
			if (result.header.success) {
				if (!userId) {
					user.id = result.body;
				}
				window.closeDlg(user);
			}
		});
		return false;
	});

});