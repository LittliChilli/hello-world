var app = angular.module('myApp', []);
app.controller('myCtrl', function ($scope, $http, $location) {
    var api = $api.with_http($http, $location);

    var checkbox = $checkbox.init();

    $scope.is_show_cart_number = false;
    $scope.shop_cart_number = 0;
    $scope.is_edit_status = false;

    $scope.shop_cart_order = "";
    $scope.total_price = 0;
    $scope.items = [];

    /**
     * 查询用户购物车信息
     */
    $scope.query_shop_cart_list = function () {

        var dataInfo = {appToken: api.getApptoken()};
        $http.get('/singapore/userOrderDateils/show_shopping_cart', {params: dataInfo}).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.items = res.data.userOrderDetailsList;
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    /**
     * 购物车商品数量变化
     * @param id
     * @param delta
     */
    $scope.update_num = function (id, delta) {

        $scope.items = $scope.items.map(function (item) {
            if (item.id == id) {
                if ((item.num + delta) < 1) {
                    Toast('不能减了');
                } else if ((item.num + delta) > 100) {
                    Toast('不能加了');
                } else {
                    item.num += delta;
                }
            }
            return item;
        });
    }

    /**
     * 删除购物车商品
     */
    $scope.delete_item = function (id) {

        var dataInfo = {appToken: api.getApptoken(), ids: id};
        $http.post('/singapore/userOrderDateils/delete_shopping_cart', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.query_shop_cart_list();
                $scope.query_shop_cart_number();
                $scope.is_show_cart_nmuber();
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.calculate_total_price = function () {

        var checkboxR = document.getElementsByName("checkbox");

        var shop_cart_order = "";
        var total_price = 0;

        for (var i = 0; i < checkboxR.length; i++) {
            if (checkboxR[i].checked) {
                $scope.items = $scope.items.map(function (item) {
                    if (item.id == checkboxR[i].value) {
                        shop_cart_order += item.id + ",";
                        total_price += item.num * item.goodsPrice;
                    }
                    return item;
                });
            }
        }

        $scope.shop_cart_order = shop_cart_order;
        $scope.total_price = total_price;
    }

    $scope.buy = function () {

        if ($scope.shop_cart_order == "") {
            Toast("请选择结算商品");
            return;
        }

        var dataInfo = {appToken: api.getApptoken(), shop_cart_order: $scope.shop_cart_order};
        $http.post('/singapore/order/orderConfirm_byCart', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                window.location.href = "my_order_detail.html?order_sn=" + res.data.orderMap.UserOrder.orderSn;
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    /**
     * 修改购物车状态
     */
    $scope.is_edit = function () {
        $scope.is_edit_status = true;
    }

    /**
     * 修改购物车
     */
    $scope.save_shop_cart_goods = function () {

        var edit_shop_cart = "";

        $scope.is_edit_status = false;

        if ($scope.items == undefined || $scope.items.length == 0) {
            return;
        }

        $scope.items = $scope.items.map(function (item) {
            edit_shop_cart += item.id + ":" + item.num + ",";
            return item;
        });

        var dataInfo = {appToken: api.getApptoken(), saveShopCartInfoStr: edit_shop_cart};
        $http.post('/singapore/userOrderDateils/update_shopping_cart', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.query_shop_cart_list();
                $scope.query_shop_cart_number();
                $scope.is_show_cart_nmuber();
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    /**
     * 复选框选择效果
     */
    $scope.select_checkbox = function () {
        checkbox.select_checkbox_JS("checkbox", "checkbox_all");
        $scope.calculate_total_price();
    }

    /**
     * 复选框全选效果
     */
    $scope.select_checkbox_all = function () {
        checkbox.select_checkbox_all_JS("checkbox", "checkbox_all");
        $scope.calculate_total_price();
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

    $scope.query_shop_cart_list();
    $scope.query_shop_cart_number();
    $scope.is_show_cart_nmuber();
});