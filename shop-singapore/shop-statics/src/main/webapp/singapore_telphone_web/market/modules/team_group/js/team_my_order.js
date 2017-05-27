var app = angular.module('myApp', []);
app.controller('myCtrl', function ($scope, $http, $location) {
    var api = $api.with_http($http, $location);

    $scope.shadow_back = false;
    $scope.shadow = false;

    $scope.team_group_list = [];

    //获取url中"?"符后的字串
    var theParam = api.getRequestParam($location.absUrl());
    $scope.team_order_status = theParam['team_order_status'];

    $scope.back = function () {
        window.location.href = "/static/singapore_telphone_web/market/my.html";
    }

    $scope.show_all_team_order = function () {

        var dataInfo = {appToken: api.getApptoken(), team_order_status: $scope.team_order_status};
        $http.get('/team_group/order/query_my_open_team_list', {params: dataInfo}).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.team_group_list = res.data.team_group_list.map(function (item) {

                    item.receipt_address = JSON.parse(item.receiveAddress);
                    return item;
                });
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.share = function (team_id,img_pic) {

        var share_url = encodeURI($location.absUrl().split('singapore_telphone_web')[0] + "singapore_telphone_web/market//modules/team_group/team_join_goods_detail.html?team_id=" + team_id);

        var description = '快来和我一起拼团吧！';
        var title = '拼团大优惠';
        var imageUrl = img_pic;

        onMenuShareTimeline(description, title, share_url, imageUrl);

        $scope.shadow_back = true;
        $scope.shadow = true;
    }

    $scope.not_share = function () {
        $scope.shadow_back = false;
        $scope.shadow = false;
    }

    $scope.confirm_receipt_shop_order = function (team_member_id) {

            var dataInfo = {appToken: api.getApptoken(), team_member_id: team_member_id};
            $http.post('/team_group/order/confirm_my_team_order', dataInfo, api.postConfig()).success(function (res) {
                // 响应成功
                if (api.resultHandle(res)) {

                    $scope.team_group_list = $scope.team_group_list.map(function (item) {

                        if (item.team_member_id == team_member_id) {
                            item.teamStatusStr = "已收货";
                        }

                        return item;
                    });
                }
            }).error(function (res) {
                // 处理响应失败
                api.resultHandle(res);
            });
    }

    $scope.show_all_team_order();

    initWx();
});