var app = angular.module('myApp', []);
app.controller('myCtrl', function ($scope, $http, $location) {
    var api = $api.with_http($http, $location);

    //获取url中"?"符后的字串
    var theParam = api.getRequestParam($location.absUrl());

    // 是否跳转页面
    $scope.is_jump = theParam['is_jump'];

    // 地址id
    $scope.receiverId = theParam['receiver_id'];

    $scope.receiver = '';
    $scope.telephone = '';
    $scope.receiptAddress = '';

    $scope.back = function () {
        window.history.go(-1);
    }

    $scope.queryAddressDetailShop = function () {

        var dataInfo = {appToken: api.getApptoken(), id: $scope.receiverId};
        $http.post('/singapore/address/query_address_detail', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                $scope.receiver = res.data.receiptAddress.receiver;
                $scope.telephone = res.data.receiptAddress.telephone;
                $scope.receiptAddress = res.data.receiptAddress.receiptAddress;
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    if ($scope.receiverId != undefined || $scope.receiverId != null) {
        $scope.operate = "编辑地址";
        $scope.queryAddressDetailShop();
    } else {
        $scope.operate = "新增地址";
    }

    $scope.savaAddressShop = function () {
        if ($scope.receiverId != undefined || $scope.receiverId != null) {
            $scope.updateAddressShop();
        } else {
            $scope.addAddressShop();
        }
    }

    $scope.addAddressShop = function () {

        var dataInfo = {
            appToken: api.getApptoken(),
            receiver: $scope.receiver,
            telephone: $scope.telephone,
            receiptAddress: $scope.receiptAddress
        };
        $http.post('/singapore/address/add_recAddress', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                var href = "addresses.html";

                if ($scope.is_jump != undefined && $scope.is_jump == 'jump') {

                    // 跳转控制
                    if (!!window.localStorage.referer_address && window.localStorage.referer_address != "") {
                        href = window.localStorage.referer_address;
                        window.localStorage.referer_address = "";
                    }
                }

                window.location.href = href;
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

    $scope.updateAddressShop = function () {

        var dataInfo = {
            appToken: api.getApptoken(),
            id: $scope.receiverId,
            receiver: $scope.receiver,
            telephone: $scope.telephone,
            receiptAddress: $scope.receiptAddress
        };
        $http.post('/singapore/address/update_recAddress', dataInfo, api.postConfig()).success(function (res) {
            // 响应成功
            if (api.resultHandle(res)) {
                window.location.href = "addresses.html";
            }
        }).error(function (res) {
            // 处理响应失败
            api.resultHandle(res);
        });
    }

});