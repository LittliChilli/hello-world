var app = angular.module('myApp', []);
app.controller('myCtrl', function ($scope, $http, $location) {
    var api = $api.with_http($http, $location);

    //获取url中"?"符后的字串
    var theParam = api.getRequestParam($location.absUrl());

    // 是否跳转页面
    $scope.team_id = theParam['team_id'];

    $scope.shop_number = 1;
    $scope.goods_total_price = 0;
    $scope.goods_details_show_pic = null;
    $scope.is_leader = false;

    //获取url中"?"符后的字串
    var theParam = api.getRequestParam($location.absUrl());

    // 是否跳转页面
    var goodsId = theParam['goods_id'];

    $scope.back = function () {
        window.history.go(-1);
    }

    $scope.join_team = function () {

        var dataInfo = {appToken: api.getApptoken(), team_id: $scope.team_id};
        $http.post('/team_group/order/join_open_team', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                Toast("参团成功");

                setTimeout(function () {
                    window.location.href = "team_my_order.html"
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
    $scope.query_team_goods_detail = function () {

        var dataInfo = {appToken: api.getApptoken(), team_id: $scope.team_id};
        $http.get('/team_group/goods/query_team_detail_by_team_id', {params: dataInfo}).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.is_leader = res.data.isLeader;

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

                $scope.team_order = res.data.TeamOrderRecord;
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

    $scope.query_team_goods_detail();

});