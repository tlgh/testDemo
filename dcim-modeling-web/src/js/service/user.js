/**
 * Created by boil on 2015-12-14.
 */
define([
    "main",
    "model/user",
    "model/organization",
    "util/string",
    "util/array"
], function (App, userModel, organizationModel, stringUtil, arrayUtil) {
    App.Services.User = function () {
        /*userModel = new App.Models.User();
         stringUtil = new App.Utils.StringUtil();
         arrayUtil = new App.Utils.ArrayUtil();*/
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
        login: function(username, password, callback) {
			password = stringUtil.md5(password);
			userModel.login(username, password, function(result) {
				if (result.header.success) {
					var user = result.body;
					window.sessionStorage.setItem(App.Constants.SESSION_USER_KEY, JSON.stringify(user));
				}
				callback(result);
			});
		},
		logoff: function(){
			window.sessionStorage.removeItem(App.Constants.SESSION_USER_KEY);
		},
		organizationTree: function(success) {
			organizationModel.list(function(result){
				if (result.header.success) {
					var list = result.body;
					result.body = arrayUtil.toTree(list, {parentKey:'parent.id'});
				}
				success(result);
			});
		}
    };
    
	function toTree(array, parentId) {
		var list = arrayUtil.findBy(array, [{
			key: 'org.id',
			value: orgId
		}]);
		for (var i = 0; i < list.length; i++) {
			list.children = toTree(array, list[i].id);
		}
		return list;
	}
    App.Services.User.prototype.constructor = App.Services.User;
    return new App.Services.User();
});
