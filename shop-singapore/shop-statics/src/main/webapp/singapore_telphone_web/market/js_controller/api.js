var $api = (function () {
    function API(http, location) {
        this.http = http;
        this.location = location;
    }

    /**
     * post config消息头配置
     * @returns {{headers: {Content-Type: string}, transformRequest: postCfg.transformRequest}}
     */
    function postConfig() {
        var postCfg = {
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function (data) {
                return $.param(data);
            }
        };

        return postCfg;
    }

    /**
     * post config消息头配置
     * @returns {{headers: {Content-Type: string}, transformRequest: postCfg.transformRequest}}
     */
    API.prototype.postConfig = function () {
        return postConfig();
    }

    /**
     * 响应返回结果公共处理
     */
    API.prototype.resultHandle = function (res) {

        return resultHandle(res);
    }

    /**
     * 响应返回结果公共处理
     * @param data
     */
    function resultHandle(res) {
        if (res.result == '1014') {
            // 进入地址管理页
            window.localStorage.referer_address = window.location.href;
            window.location.href = this.location.href.split('singapore_telphone_web')[0] + "singapore_telphone_web/market/addresses_operate.html?is_jump=jump";
            return false;
        }else if (res.result == '1013') {
            getIntoLogin();
            return false;
        } else if (res.result != '0') {
            Toast(res.msg);
            return false;
        }
        return true;
    }

    /**
     * 未登陆注册的用户进入登录页
     */
    function getIntoLogin() {
        window.localStorage.referer = window.location.href;
        window.location.href = this.location.href.split('singapore_telphone_web')[0] + "singapore_telphone_web/market/login.html";
    }

    /**
     * 未登陆注册的用户进入登录页
     */
    API.prototype.getIntoLogin = function () {
        getIntoLogin();
    }

    function getApptoken() {
        return window.localStorage.appToken;
    }

    API.prototype.getApptoken = function () {
        return getApptoken();
    }

    /**
     * 查询用户购物车数量
     */
    API.prototype.query_shop_cart_number = function ($scope) {

        var dataInfo = {appToken: getApptoken()};
        this.http.get('/singapore/userOrderDateils/query_shopping_cart_number', {params: dataInfo}).success(function (res) {
            // 响应成功
            if (resultHandle(res)) {
                $scope.shop_cart_number = res.data.shopCartNumber;
                $scope.is_show_cart_nmuber();
            }
        }).error(function (res) {
            // 处理响应失败
            resultHandle(res);
        });
    }

    API.prototype.getRequestParam = function(url) {

        var theRequestParam = new Object();
        if (url.indexOf("?") != -1) {
            var str = url.substr(url.indexOf("?") + 1);
            strs = str.split("&");
            for(var i = 0; i < strs.length; i ++) {
                theRequestParam[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
            }
        }
        return theRequestParam;
    }

    //more apis
    return {
        with_http: function (http, location) {
            return new API(http, location);
        }
    }
})();