define([
	"sweetAlert"
], function() {
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
			/***Start Custom Properties***/
			showLoading: false,
			showErrorMsg: true,
			showSuccessMsg: false,
			msgAlertProvider: 'sweet-alert',
			resultHandler: null,
			/***end Custom Properties***/
			dataType: 'json',
			error: function(jqXHR, textStatus, errorThrown) {
				switch (jqXHR.status) {
					case (500):
						alertMsg("服务器系统内部错误", false, this.msgAlertProvider);
						break;
					case (401):
						alertMsg("未登录", false, this.msgAlertProvider);
						break;
					case (403):
						alertMsg("无权限执行此操作", false, this.msgAlertProvider);
						break;
					case (408):
						alertMsg("请求超时", false, this.msgAlertProvider);
						break;
					default:
						alertMsg("未知错误:status-" + jqXHR.status, false, this.msgAlertProvider);
				}
			},
			success: function(result) {
				//请求结果错误且需要输出或
				if ((this.showErrorMsg && !result.header.success) || (this.showSuccessMsg && result.header.success)) {
					alertMsg(result.header.message, result.header.success, this.msgAlertProvider);
				}

				if (this.resultHandler) {
					this.resultHandler.apply(this, [result]);
				}
			},
			beforeSend: function() {
				if (this.showLoading) {

				}
			},
			complete: function() {
				if (this.showLoading) {

				}
			}
		});
	}

	function alertMsg(msg, type, msgAlertProvider) {
		switch (msgAlertProvider) {
			case 'sweet-alert':
				sweetAlertMsg(msg, type);
				break;
			default:
				alert(msg);
				break;
		}
	}

	function sweetAlertMsg(msg, type) {
		swal({
			title: msg,
			type: type ? 'success' : 'error'
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