var app = angular.module('myApp', []);
app.controller('myCtrl', function ($scope, $http, $location) {
    var api = $api.with_http($http, $location);

    $scope.shop_cart_number = 0;
    $scope.shop_number = 1;
    $scope.goods_total_price = 0;
    $scope.goods_details_show_pic = null;

    //获取url中"?"符后的字串
    var theParam = api.getRequestParam($location.absUrl());

    // 是否跳转页面
    var goodsId = theParam['goods_id'];

    function go_to(to) {
        var url = window.location.href;
        var login_url = url.replace(/login.html/, to);
        window.location.href = login_url;
    }

    $scope.back = function () {
        window.history.go(-1);
    }

    $scope.put_into_cart = function () {

        var dataInfo = {appToken: api.getApptoken(), goodsId: goodsId, number: $scope.shop_number};
        $http.post('/singapore/userOrderDateils/add_shopping_cart', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.shop_cart_number = $scope.shop_cart_number + $scope.shop_number;
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.goto_shop_cart = function () {
        window.location.href = "shopping_cart.html";
    }

    $scope.buy = function () {

        var dataInfo = {appToken: api.getApptoken(), goodsId: goodsId,shop_number :$scope.shop_number};
        $http.post('/singapore/order/orderConfirm_Direct', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                window.location.href = "my_order_detail.html?order_sn=" + res.data.resultMap.UserOrder.orderSn;
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.update_num = function (delta) {

        if (($scope.shop_number + delta) < 1) {
            Toast('不能减了');
        } else if (($scope.shop_number + delta) > 100) {
            Toast('不能加了');
        } else {
            $scope.shop_number += delta;

            $scope.goods_total_price = $scope.goods.goodsPrice * $scope.shop_number;
        }
    }

    /**
     * 判断是否展示图片
     * @param pic
     * @returns {boolean}
     */
    $scope.is_show_pic = function (pic) {

        if (pic == undefined || pic == null || pic == "") {
            return false;
        }
        return true;
    }

    /**
     * 获取商品详情
     * @param goodsId
     */
    $scope.query_goods_detail = function () {

        var dataInfo = {goodsId: goodsId};
        $http.get('/singapore/goods/singapore_query_goodsDetail', {params: dataInfo}).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.goods_id = res.data.Goods.id;
                $scope.goods_total_price = res.data.Goods.goodsPrice;

                var show_pic = [];
                show_pic.push(res.data.Goods.goodsDetailsShowPic);
                $scope.goods_details_show_pic = show_pic;

                $scope.goods = [res.data.Goods].map(function(item) {
                    var pics = [];
                    for(var i=1; i<=10; i++) {
                        if ($scope.is_show_pic(item["goodsDetailsPic" + i])) {
                            pics.push(item["goodsDetailsPic" + i]);
                        }
                    }
                    item.detailPics = pics;

                    return item;
                })[0];
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    /**
     * 查询用户购物车数量
     */
    $scope.query_shop_cart_number = function () {

            var dataInfo = {appToken: api.getApptoken(), goods_id: goodsId};
            $http.get('/singapore/userOrderDateils/query_shopping_cart_number', {params: dataInfo}).success(function (res) {
                // 响应成功
                if (api.resultHandle(res)) {
                    $scope.shop_cart_number = res.data.shopCartNumber;
                }
            }).error(function (res) {
                // 处理响应失败
                api.resultHandle(res);
            });
    }

    $scope.query_goods_detail();
    $scope.query_shop_cart_number();

});