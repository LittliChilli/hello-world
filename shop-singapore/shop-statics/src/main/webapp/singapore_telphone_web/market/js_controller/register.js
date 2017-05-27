var app = angular.module('myApp', []);
app.controller('myCtrl', function ($scope, $http, $location) {
    var api = $api.with_http($http, $location);

    $scope.back = function () {
        window.history.go(-1);
    }

    /**
     * 注册
     * @param username
     * @param password
     */
    $scope.shop_register = function () {

        var dataInfo = {user_name: $scope.user_name, password: $scope.password, re_password: $scope.re_password};
        $http.post('/singapore/userlogin/register_user', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                window.localStorage.appToken = res.data.APP_TOKEN;

                // 注册跳转控制
                var href = !!window.localStorage.referer ? window.localStorage.referer : $location.absUrl().split('singapore_telphone_web')[0] + "/singapore_telphone_web/market/login.html";
                window.location.href = href;
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });

    }
});