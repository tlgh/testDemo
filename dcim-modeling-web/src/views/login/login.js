/**
 * Created by boil on 2015-12-14.
 */
define(['service/user'], function ( userService) {
    var user = userService.getCurrentUser();
    if (user) {
        alert('用户：' + user.name + "，已经登陆！");
        pageToIndex();
    }

    $('#loginForm').submit(function () {
        var loginName = $('#inputLoginName').val();
        var password = $('#inputPassword').val();
        userService.login(loginName, password, function (result) {
            if (result.header.success) {
                pageToIndex();
            } else {
                alert(result.header.message);
            }
        });
        return false;
    });
    function pageToIndex() {
        window.location = '../user/list.html';
    }
});

