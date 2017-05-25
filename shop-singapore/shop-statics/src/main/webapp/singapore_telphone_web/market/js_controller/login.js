var app = angular.module('myApp', []);
app.controller('myCtrl', function ($scope, $http, $location) {
    var api = $api.with_http($http, $location);

    $scope.username = "";
    $scope.password = "";

    $scope.register = function () {
        window.location.href = "register.html";
    }

    $scope.back = function () {
        window.history.go(-1);
    }

    /**
     * 登录
     */
    $scope.shop_login = function () {

        var dataInfo = {login_name: $scope.username, password: $scope.password};
        $http.post('/singapore/userlogin/login_user', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                window.localStorage.appToken = res.data.APP_TOKEN;

                // 登录跳转控制
                var href = !!window.localStorage.referer ? window.localStorage.referer : $location.absUrl().split('singapore_telphone_web')[0] + "/singapore_telphone_web/market/login.html";
                window.location.href = href;
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }
});