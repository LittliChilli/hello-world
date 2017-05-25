var app = angular.module('myApp', []);
app.controller('myCtrl', function ($scope, $http, $location, $q) {
    var api = $api.with_http($http, $location);

    $scope.is_modify_address = false;
    $scope.receipt_address_number = 0;
    $scope.is_show_modify_button = false;
    $scope.is_join_index = false;

    var locationAbsUrl = $location.absUrl().split("?");
    if (locationAbsUrl[1] != undefined && locationAbsUrl[1] != null) {
        var location_search = locationAbsUrl[1].split("=");
        if (location_search[0] == "order_sn") {
            $scope.order_sn = location_search[1];
        }
    }

    $scope.back = function () {
        window.history.go(-1);
    }

    $scope.show_order_detail = function () {

        var dataInfo1 = {appToken: api.getApptoken(), order_sn : $scope.order_sn};
        var request1 = $http.get('/singapore/order/query_my_order_detail', {params:dataInfo1});

        var dataInfo2 = {appToken: api.getApptoken()};
        var requset2 = $http.get('/singapore/address/query_recAddressAll', {params:dataInfo2});
        $q.all([request1, requset2]).then(function(arr) {
            var res1 = arr[0].data,
                res2 = arr[1].data;

            // 响应成功
            if (api.resultHandle(res1) && api.resultHandle(res2)) {
                if (res1.data.relationList != undefined && res1.data.relationList != null) {
                    var relationInfo = res1.data.relationList[0];
                    $scope.convertAddress(relationInfo.userOrder.receiveAddress);

                    $scope.relation = relationInfo;

                    $scope.receipt_address_List = res2.data.receipt_address_List;
                    $scope.receipt_address_number = res2.data.receipt_address_number ? res2.data.receipt_address_number : $scope.receipt_address_number;

                    if ($scope.relation.userOrder.orderStatus == '等待采购' && $scope.receipt_address_number != 1) {
                        $scope.is_show_modify_button = true;
                    }
                }
            }
        }).catch(function(res) {
            api.resultHandle(res);
        });
    }

    $scope.convertAddress = function(addressJson) {
        $scope.receipt_address = JSON.parse(addressJson);
    }

    $scope.show_all_address = function () {
        $scope.is_modify_address = true;
    }

    $scope.updateRecAddress = function (rec_address) {
        $scope.receipt_address = rec_address;
        $scope.is_modify_address = false;
    }
    
    $scope.shop_order = function () {

        if ($scope.is_modify_address){
            Toast("请确认收货地址");
            return;
        }

        var dataInfo = {appToken: api.getApptoken(), order_sn: $scope.order_sn,address_id :$scope.receipt_address.id};
        $http.post('/singapore/order/order_create_by_cart', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                Toast("下单成功", 1000);
                $scope.is_join_index = true;

                setInterval(function () {
                    $scope.$apply(function () {
                        if ($scope.is_join_index) {
                            window.location.href = "../index.html";
                        }
                    });
                }, 1000);
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.confirm_my_order = function (order_sn) {
        var dataInfo = {appToken: api.getApptoken(), order_sn: order_sn};
        $http.post('/singapore/order/confirm_my_order', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.show_order_detail();
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.show_order_detail();

});