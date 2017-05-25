var app = angular.module('myApp', []);
app.controller('myCtrl', function ($scope, $http, $location) {
    var api = $api.with_http($http, $location);

    $scope.back = function () {
        window.history.go(-1);
    }

    $scope.shopAddress = function () {

        var dataInfo = {appToken: api.getApptoken()};
        $http.get('/singapore/address/query_recAddressAll', {params:dataInfo}).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.receipt_address_List = res.data.receipt_address_List;
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.updateAddressShop = function (id) {
        window.location.href = "addresses_operate.html?receiver_id=" + id;
    }

    $scope.addAddressShop = function () {
        window.location.href = "addresses_operate.html";
    }

    $scope.deleteAddressShop = function (id) {

        var dataInfo = {appToken: api.getApptoken(), id: id};
        $http.post('/singapore/address/delete_recAddress', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.shopAddress();
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.updateDefaultAddress = function (id) {

        var dataInfo = {appToken: api.getApptoken(), id: id};
        $http.post('/singapore/address/default_recAddress', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.shopAddress();
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.shopAddress();
});