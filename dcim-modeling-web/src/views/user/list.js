/**
 * Created by boil on 2015-12-14.
 */
define(['service/user','model/organization', 'ZY'], function (userService, organizationModel) {

    var tree = null, grid = null;

    var columns = [
        {name: "username", caption: "登录名", width: 120, type: "String"},
        {name: "name", caption: "姓名", width: 120, type: "String"},
        {name: "birthday", caption: "生日", width: 200, type: "Date", format: "yyyy-MM-dd"},
        {name: "email", caption: "email", width: 200, type: "String"}
    ];


	//state
    function gotoPage(pageSize, pageIndex) {
        var organizationId = tree.getSelectedNode().data.id;
        organizationModel.users(organizationId, function(result){
        	if(result.header.success){
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
				if(organization){
					console.log(organization);
				}
			}
		});
	});
	
	$('#btnAddUser').click(function() {
        var organizationId = tree.getSelectedNode().data.id;
		window.openDlg({
			url: "user-dialog.html?organizationId="+organizationId, //在应用中需要修改为特定页面的url
			width: 600,
			height: 580,
			title: "新增用户",
			callback: function(organization) {
				if(organization){
					console.log(organization);
				}
			}
		});
	});
	
	$('#btnLogoff').click(function(){
		userService.logoff();
		window.location = '../login/login.html';
	})
});
