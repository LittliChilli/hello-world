var app = angular.module('myApp', []);
app.controller('myCtrl', function ($scope, $http, $location) {
    var api = $api.with_http($http, $location);

    //获取url中"?"符后的字串
    var theParam = api.getRequestParam($location.absUrl());
    $scope.order_status = theParam['order_status'];

    $scope.back = function () {
        window.history.go(-1);
    }

    $scope.show_all_order = function () {

        var dataInfo = {appToken: api.getApptoken(), order_status: $scope.order_status};
        $http.get('/singapore/order/query_my_order_list', {params:dataInfo}).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.relation_list = res.data.relationList;
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.query_order_detail = function (order_sn) {
        window.location.href = "show_order_detail.html?order_sn=" + order_sn;
    }

    $scope.cancle_my_order = function (order_sn) {

        var dataInfo = {appToken: api.getApptoken(), order_sn: order_sn};
        $http.post('/singapore/order/cancel_my_order', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.show_all_order();
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.show_all_order();
});