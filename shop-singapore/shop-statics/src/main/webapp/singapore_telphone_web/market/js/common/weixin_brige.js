/**
 * Created by sheldon on 2016/4/14.
 */

var url = window.location.href;
var targetUrl = url.split('#')[0];
console.log(targetUrl);
var timeStamp = new Date();
function initWx() {
    var req = new XMLHttpRequest();
    if (req) {
        req.open("GET", "/singapore/weixin/getJsSDKConfig?targetUrl=" + targetUrl + '&timeStamp=' + timeStamp, true);
        req.onreadystatechange = function () {
            if (req.readyState == 4) {
                if (req.status == 200) {
                    var resText = req.responseText;
                    var res = JSON.parse(resText);
                    console.log(res);
                    if (res.result == 0) {
                        wx.config({
                            debug: false,
                            appId: res.data.jsSDKConfig.appId,
                            timestamp: res.data.jsSDKConfig.timestamp,
                            nonceStr: res.data.jsSDKConfig.nonceStr,
                            signature: res.data.jsSDKConfig.signature,
                            jsApiList: [
                                'chooseImage',
                                'uploadImage',
                                'chooseWXPay',
                                'onMenuShareTimeline',
                                'onMenuShareAppMessage'
                            ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                        });
                        wx.ready(function () {
                            console.log('ready')
                        });
                        wx.error(function (res) {
                            //alert(res.errMsg);
                        });
                    } else {
                        console.log(res);
                    }
                } else {
                    //Toast('获取微信配置失败');
                }
            }
        };

        req.send(null);
    }
}

// 事件加载
function onMenuShareTimeline(title1, desc1, link1, imgUrl1) {

    wx.onMenuShareAppMessage({
        title: title1, // 分享标题
        desc: desc1, // 分享描述
        link: link1, // 分享链接
        imgUrl: imgUrl1, // 分享图标
        type: '', // 分享类型,music、video或link，不填默认为link
        dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
        success: function () {
            console.log('config-ok');
            // 用户确认分享后执行的回调函数
        },
        cancel: function () {
            // 用户取消分享后执行的回调函数
        }
    });
}

