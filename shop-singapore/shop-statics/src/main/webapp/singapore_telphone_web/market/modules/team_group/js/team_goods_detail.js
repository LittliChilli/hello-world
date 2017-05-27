var app = angular.module('myApp', []);
app.controller('myCtrl', function ($scope, $http, $location) {
    var api = $api.with_http($http, $location);

    $scope.shop_number = 1;
    $scope.goods_total_price = 0;
    $scope.goods_details_show_pic = null;

    //获取url中"?"符后的字串
    var theParam = api.getRequestParam($location.absUrl());
    var goodsId = theParam['goods_id'];

    function go_to(to) {
        var url = window.location.href;
        var login_url = url.replace(/login.html/, to);
        window.location.href = login_url;
    }

    $scope.back = function () {
        window.history.go(-1);
    }

    $scope.open_team = function () {

        var dataInfo = {appToken: api.getApptoken(), goodsId: goodsId};
        $http.post('/team_group/order/confirm_open_team', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                Toast("开团成功,邀请好友一起");

                setTimeout(function () {
                    window.location.href = "team_my_order.html";
                }, 1000);
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    /**
     * 获取商品详情
     * @param goodsId
     */
    $scope.query_goods_detail = function () {

        var dataInfo = {goodsId: goodsId};
        $http.get('/team_group/goods/query_detail_by_id', {params: dataInfo}).success(function (res) {
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

    $scope.query_goods_detail();

});