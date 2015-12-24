/**
 * Created by boil on 2015-12-14.
 */
define([
    "main"
], function (App) {
    var basePath;
	var modulePath = '/user';
    App.Models.User = function () {
        basePath = App.Constants.BASE_PATH;
    };

    App.Models.User.prototype = {
        page: function (organizationId, name, success) {
            $.ajax({
				type: "GET",
				url: basePath + modulePath + "/list",
				data: {
					organizationId: organizationId,
					name: name
				},
				dataType: "json",
				success: success
			});
        },
		add: function(user, success) {
			$.ajax({
				type: "POST",
				url: basePath + modulePath + "/",
				data: JSON.stringify(user),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
				success: success
			});
		},
		login: function(username, password, success) {
			$.ajax({
				type: "POST",
				url: basePath + modulePath + "/login",
				data: {
					username: username,
					password: password
				},
                dataType: 'json',
				success: success
			});
		}
    };
    App.Models.User.prototype.constructor = App.Models.User;
    return new App.Models.User();
});
