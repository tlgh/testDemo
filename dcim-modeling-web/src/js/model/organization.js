/**
 * Created by boil on 2015-12-14.
 */
define([
	"main",
], function(App) {
	var basePath;
	var modulePath = '/organization';
	App.Models.Organization = function() {
		basePath = App.Constants.BASE_PATH;
	};

	App.Models.Organization.prototype = {
		list: function(success) {
			$.ajax({
				type: "GET",
				url: basePath + modulePath + "/list",
				dataType: "json",
				success: success
			});
		},
		add: function(organization, success) {
			$.ajax({
				type: "POST",
				url: basePath + modulePath + "/",
				data: JSON.stringify(organization),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
				success: success
			});
		},
		users: function(organizationId, success) {
			$.ajax({
				type: "GET",
				url: basePath + modulePath + "/" + organizationId + '/users',
				dataType: "json",
				success: success
			});
		}
	};




	App.Models.Organization.prototype.constructor = App.Models.Organization;
	return new App.Models.Organization();
});