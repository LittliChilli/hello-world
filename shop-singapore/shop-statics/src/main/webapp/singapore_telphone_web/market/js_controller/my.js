var app = angular.module('myApp', []);
app.controller('myCtrl', function ($scope, $http, $location) {
    var api = $api.with_http($http, $location);

    $scope.is_show_cart_number = false;
    $scope.shop_cart_number = 0;
    $scope.isLogin = false;

    $scope.order_buying_num = 0;
    $scope.order_alreadyBuy_num = 0;
    $scope.order_confirmReceived_num = 0;

    $scope.login = function () {
        api.getIntoLogin($location);
    }

    $scope.shop_user_center = function () {

        var dataInfo = {appToken: api.getApptoken()};
        $http.get('/singapore/center/query_user_info', {params: dataInfo}).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                if (res.data.user != undefined) {
                    $scope.user_name = res.data.user.nickName;
                    $scope.isLogin = true;

                    $scope.query_shop_cart_number();
                    $scope.is_show_cart_nmuber();
                    $scope.query_order_status_num_1('buying');
                    $scope.query_order_status_num_2('alreadyBuy');
                    $scope.query_order_status_num_3('confirmReceived');
                }
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.login_out = function () {
        var dataInfo = {appToken: api.getApptoken()};
        $http.post('/singapore/userlogin/loginOut_user', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                window.localStorage.appToken = "";
                window.location.href = "index.html";
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.manager_addresses = function () {
        window.location.href = "addresses.html";
    }

    $scope.my_orders = function () {
        window.location.href = "my_order.html";
    }

    $scope.goto_order_list = function (order_status) {
        window.location.href = "my_order.html?order_status=" + order_status;
    }

    $scope.my_team_group = function () {
        window.location.href = "/static/singapore_telphone_web/market/modules/team_group/team_my_order.html";
    }

    $scope.query_order_status_num_1 = function (order_status) {

        var dataInfo = {appToken: api.getApptoken(), order_status: order_status};
        $http.get('/singapore/center/query_user_order_number', {params: dataInfo}).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.order_buying_num = res.data.orderNum;
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.query_order_status_num_2 = function (order_status) {

        var dataInfo = {appToken: api.getApptoken(), order_status: order_status};
        $http.get('/singapore/center/query_user_order_number', {params: dataInfo}).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.order_alreadyBuy_num = res.data.orderNum;
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.query_order_status_num_3 = function (order_status) {

        var dataInfo = {appToken: api.getApptoken(), order_status: order_status};
        $http.get('/singapore/center/query_user_order_number', {params: dataInfo}).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.order_confirmReceived_num = res.data.orderNum;
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    /**
     * 是否展示购物车商品数量
     */
    $scope.is_show_cart_nmuber = function () {
        if ($scope.shop_cart_number > 0) {
            $scope.is_show_cart_number = true;
        } else {
            $scope.is_show_cart_number = false;
        }
    }

    /**
     * 查询用户购物车数量
     */
    $scope.query_shop_cart_number = function () {
        api.query_shop_cart_number($scope);
    }

    //获取商品列表
    $scope.shop_user_center();

    $scope.callMmfq = function () {
        window.location.href = 'tel://' + '400-711-8898';
    }
});