// 删除按钮
function msgClose(functionName, data) {
	hiddenMsg();

	functionApplyMsg(functionName, data);
}

/**
 * 判断是一个function,并执行
 * 
 */
function functionApplyMsg(functionName, data) {

	if (data == null) {
		if (typeof (functionName) === 'function') {
			functionName.apply(this);
		}
	} else {
		functionName = eval(functionName);

		if (typeof (functionName) === 'function') {
			functionName.apply(this, [ data ]);
		}
	}
}

/**
 * 隐藏弹出框
 */
function hiddenMsg() {
	jQuery('.none').fadeOut(400);
}

/**
 * 显示弹出框
 */
function displayMsg() {
	jQuery('.none').fadeIn(400);
}
/**
 * 数量消息弹出框
 * 
 * @param msgTextCount
 */
function alertSysMsgCount(msgTextCount) {
	alertSysMsgTextCount(msgTextCount, null, null,"确认",null,null);
}
function alertSysMsgTextCount(msgTextCount, cancleMethod, cancleData,okButName,okClickMethod,okData) {
	showMsgCount(msgTextCount, cancleMethod, cancleData,okButName,okClickMethod,okData);
	displayMsg();
}

function showMsgCount(msgTextCount, cancleMethod, cancleData,okButName,okClickMethod,okData) {

	$("#sysMsgText").empty();

	var contentArray = '<div class="none" id="one">'
			+ '<div class="layer_content">' + '<div class="header">'
			+ '<div class="title">提示消息</div>' + '<div class="cancel" id="x">'
			+ "<a href='#' onclick='msgClose(" + cancleMethod + ","
			+ cancleData + ");'><img src='/miao/imgs/close.png'></a>"
			+ '</div></div>'
			+ '<div class="layer_content2" onclick="confirmMsgCount()">'
			+ msgTextCount + '</div>' + ' <div class="t_foot">'
			+ '<button class="btn-success btn"  onclick="confirmMsgCount();">'
			+ okButName + '</button>'

	contentArray += "</div></div></div>";
	$("#sysMsgText").html(contentArray);

}
// 跳转到站内信的地址
function confirmMsgCount() {
	window.open("/sysMsgText/toSysMsgText");
}