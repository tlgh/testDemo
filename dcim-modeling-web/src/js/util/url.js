/**
 * Created by boil on 2016-01-15.
 */
define(['main'], function(App) {

	App.Utils.UrlUtil = {

		getUrlParameter: function(key) {
			var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if (r != null) return unescape(r[2]);
			return null;
		}
	}

	return App.Utils.UrlUtil;
});