/**
 * Created by boil on 2015-12-14.
 */
define(['service/user', 'ZY'], function(userService) {

	var tree = null,
		grid = null;

	var columns = [{
		name: "username",
		caption: "登录名",
		width: 120,
		type: "String"
	}, {
		name: "name",
		caption: "姓名",
		width: 120,
		type: "String"
	}, {
		name: "sex",
		caption: "性别",
		width: 120,
		type: "String"
	}, {
		name: "birthday",
		caption: "生日",
		width: 200,
		type: "Date",
		format: "yyyy-MM-dd"
	}, {
		name: "email",
		caption: "email",
		width: 200,
		type: "String"
	}];


	//state
	function gotoPage(pageSize, pageIndex) {
		var organizationId = tree.getSelectedNode().data.id;
		userService.organizationUsers(organizationId, function(result) {
			if (result.header.success) {
				grid.bind(result.body);
			}
		});
	}

	function findUserByOrg(orgId) {
		grid.gotoPage(-1, 0);
	}


	//init datas
	var grid = new ZY.UI.Grid({
		box: "gridBox",
		columns: columns,
		selectedMode: "S",
		pageController: {
			pageRead: gotoPage
		},
		onChanged: function(row) {
			//TODO:
		}
	});

	userService.organizationTree(function(result) {
		console.log(result.body[0]);
		tree = new ZY.UI.Tree("treeBox", {
			children: "children",
			view: "name",
			extendedStyle: "accessible"
		}, result.body[0]);
		tree.children[0].open();
		tree.onChanged = function(node) {
			if (node.focused)
				findUserByOrg(node.data.id);
		};
		tree.unAccessibleFocused = function(node) {
			if (node.data.linked.invalid == true) {
				$("#deleteOrg").show();
			}
		};
		tree.children[0].focus();
	});

	//register events
	$('#btnAddOrganization').click(function() {
		window.openDlg({
			url: "organization-dialog.html", //在应用中需要修改为特定页面的url
			width: 600,
			height: 380,
			title: "新增部门",
			callback: function(organization) {
				if (organization) {
					parent = tree.findNode('id', organization.parent.id);
					if (parent) {
						parent.addChild(organization);
					}
				}
			}
		});
	});

	$('#btnEditOrganization').click(function() {
		var node = tree.getSelectedNode();
		if (!node) {
			swal("警告!", "请选中需要删除的组织机构.", "warning");
			return;
		}
		var org = node.data;
		window.openDlg({
			url: "organization-dialog.html?organizationId=" + org.id, //在应用中需要修改为特定页面的url
			width: 600,
			height: 380,
			title: "新增部门",
			callback: function(organization) {
				if (organization) {
					node.update(organization);
				}
			}
		});
	});

	$('#btnDeleteOrganization').click(function() {
		var node = tree.getSelectedNode();
		if (!node) {
			swal("警告!", "请选中需要删除的组织机构.", "warning");
			return;
		}
		var org = node.data;
		if (org.id == 'orgRoot') {
			swal("警告!", "不能删除顶级组织机构.", "warning");
			return;
		}
		if (org.children && org.children.length > 0) {
			swal("警告!", "当前组织机构存在下级机构，请先删除下级机构.", "warning");
			return;
		}
		swal({
			title: "确定这样操作吗?",
			text: "您将删除名称为：'" + org.name + "'的组织机构，确定要这样操作吗?",
			type: "warning",
			showCancelButton: true,
			cancelButtonText: "取消",
			confirmButtonColor: "#DD6B55",
			confirmButtonText: "删除!",
			closeOnConfirm: false,
			showLoaderOnConfirm: true
		}, function() {
			userService.deleteOrganization(org.id, function(result) {
				if (result.header.success) {
					tree.remove(node);
				}
			});
		});
	});

	$('#btnAddUser').click(function() {
		var organizationId = tree.getSelectedNode().data.id;
		window.openDlg({
			url: "user-dialog.html?organizationId=" + organizationId, //在应用中需要修改为特定页面的url
			width: 600,
			height: 630,
			title: "新增用户",
			callback: function(organization) {
				if (organization) {
					console.log(organization);
				}
			}
		});
	});

	$('#btnLogoff').click(function() {
		userService.logoff();
		window.location = '../login/login.html';
	});

});