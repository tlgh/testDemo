/**
 * Created by boil on 2015-12-14.
 */
"use strict"
define(['service/user', 'model/user', 'model/organization', 'util/array', 'ZY'], function(userService, userModel, organizationModel, arrayUtil) {
	var selectOrganization = $('#selectOrganization');
	var form = new ZY.UI.Form("userForm");

	//init datas
	form.setValue({});
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
		});
	});

	//register events
	$('#userForm').submit(function() {
		var user = form.getValue();
		user.birthday = user.birthday + ' 00:00:00';
		userModel.add(user, function(result) {
			alert(result.header.success ? "添加成功"　 : "添加失败");
			if (result.header.success) {
				window.closeDlg(result.body);
			}
		});
		return false;
	});

});