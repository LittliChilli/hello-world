var app = angular.module('myApp', []);
app.controller('myCtrl', function ($scope, $http, $location, $q) {
    var api = $api.with_http($http, $location);

    $scope.is_show_cart_number = false;
    $scope.shop_cart_number = 0;

    $scope.goods_category_list = [];

    /**
     * 首页商品列表展示
     * @param $scope
     */
    $scope.index_goods_show = function () {

        var category_promise = $http.get('/team_group/goods/query_all_goods_category');
        var all_goods_promise = $http.get('/team_group/goods/query_all_goods');
        $q.all([category_promise, all_goods_promise]).then(function(arr) {
            var res_category = arr[0], 
                res_goods = arr[1];
            $scope.goods_category_list = res_category.data.data.goodsCategoryList;
            var goods = res_goods.data.data.goodsList;
            $scope.goods_category_list = $scope.goods_category_list.map(function(item) {
                var goods_list = [];
                goods.forEach(function(g) {
                    //console.log(g.goodsPic);
                    if (item.id == g.categoryId) {
                        goods_list.push(g);
                    }
                });
                item.goods_list = goods_list;
                return item;
            });
        }).catch(function(res) {
            api.resultHandle(res);
        });
    }

    //获取商品详情
    $scope.detail = function (goodsId) {
        window.location.href = "team_goods_detail.html?goods_id=" + goodsId;
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
    $scope.index_goods_show();
    $scope.query_shop_cart_number();
    $scope.is_show_cart_nmuber();

});