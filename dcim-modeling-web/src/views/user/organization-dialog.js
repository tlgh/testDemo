/**
 * Created by boil on 2015-12-14.
 */
define(['service/user', 'util/array', 'ZY'], function(userService, arrayUtil) {
	var selectParent = $('#selectParent');
	var form = new ZY.UI.Form("organizationForm");

	//init datas
	form.setValue({});
	userService.organizationTree(function(result) {
		selectParent.empty();

		arrayUtil.traverseTree(result.body, function(child, level) {
			var name = child.name;
			for (var i = 0; i < level; i++) {
				name = '　' + name;
			}
			var option = $('<option>');
			option.text(name);
			option.val(child.id);
			selectParent.append(option);
		})
	});

	//register events
	$('#organizationForm').submit(function() {
		var organization = form.getValue();
		userService.addOrganization(organization, function(result) {
			if (result.header.success) {
				organization.id = result.body;
				window.closeDlg(organization);
			}
		})
		return false;
	});

});