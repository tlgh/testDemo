define(function() {
	var App = {
		Models: {},
		Views: {},
		Services: {},
		Utils: {},
		Constants: {},
		initialize: function() {
			// define Constants
			App.Constants.SESSION_USER_KEY = 'currentLoginUser';
			App.Constants.BASE_PATH = 'http://127.0.0.1:8080/dcim-modeling-server/api';

			ajaxGlobalSetting();

			loadController();
		}
	};


	/**
	 * 设置jQuery Ajax全局的参数
	 */
	function ajaxGlobalSetting() {
		$.ajaxSetup({
			error: function(jqXHR, textStatus, errorThrown) {
				switch (jqXHR.status) {
					case (500):
						alert("服务器系统内部错误");
						break;
					case (401):
						alert("未登录");
						break;
					case (403):
						alert("无权限执行此操作");
						break;
					case (408):
						alert("请求超时");
						break;
					default:
						alert("未知错误:status-" + jqXHR.status);
				}
			},
			success: function(result) {
				if(!result.header.success){
					alert(result.header.message);
				}
			},
			beforeSend: function () {
				alert(this.showLoading);
		    },
			complete: function(){
				alert(this.showLoading);
			}
		});
	}

	/**
	 * 加载页面Controller
	 */
	function loadController() {
		var html = window.location.href.toString();
		var ctrl = html.replace('.html', '.js');
		require([ctrl]);
	}
	return App;
});