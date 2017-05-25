/**
 * Created by zhouriheng on 15/10/15.
 */
(function (doc, win) {
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function () {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;
            docEl.style.fontSize = 24 * (clientWidth / 640) + 'px';
        };
    if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);


/**
 * 向IOS记录发送地址
 */
function sendUrlRecordClick(data) {
	sendUrlRecord(JSON.stringify(data));
}


/**
 * 调用IOS
 */
function sendUrlHref(url, title) {

	/**
	 * IOSOperationType,操作类型 sendUrl,发送url,dataInfo,发送参数信息
	 */
	var dataInfo = {
		"IOSOperationType" : 0,
		"sendUrl" : url,
		"dataInfo" : "",
		"title": title,
	}

	sendUrlRecordClick(dataInfo);
}

/**
 * 调用IOS支付机制
 */
function sendUrlWeiXin(data) {
	
	var dataInfo = {
			"IOSOperationType" : 1,
			"sendUrl" : "",
			"dataInfo" : JSON.stringify(data),
			"title": "",
	}

	sendUrlRecordClick(dataInfo);
}

/**
 * 调用IOS分享机制
 */
function sendUrlShareDate(data) {
	
	/**
	 * IOSOperationType,操作类型 sendUrl,发送url,dataInfo,发送参数信息
	 */
	var dataInfo = {
		"IOSOperationType" : 2,
		"sendUrl" : "",
		"dataInfo" : JSON.stringify(data),
		"title": "",
	}

	sendUrlRecordClick(dataInfo);
}

