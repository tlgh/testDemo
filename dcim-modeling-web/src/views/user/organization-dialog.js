/**
 * Created by boil on 2015-12-14.
 */
define(['service/user', 'util/array', 'util/url', 'ZY'], function(userService, arrayUtil, urlUtil) {
	var selectParent = $('#selectParent');
	var form = new ZY.UI.Form("organizationForm");


	//init datas
	var organization = {};
	var organizationId = urlUtil.getUrlParameter('organizationId');
	if (organizationId) {
		userService.getOrganization(organizationId, function(result) {
			if (result.header.success) {
				organization = result.body;
				form.setValue(organization);
			}
		});
	} else {
		form.setValue(organization);
	}
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
	}, true);

	//register events
	$('#organizationForm').submit(function() {
		var organization = form.getValue();
		userService.saveOrUpdateOrganization(organization, function(result) {
			if (result.header.success) {
				if (!organizationId) {
					organization.id = result.body;
				}
				window.closeDlg(organization);
			}
		})
		return false;
	});

});