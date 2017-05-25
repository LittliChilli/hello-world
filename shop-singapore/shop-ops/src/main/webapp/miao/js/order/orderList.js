$(document).ready(function() {
	queryOrderListByPage(1);
});

$("#searchOrder").click(function() {
	queryOrderListByPage(1);
});

// 分页查询用户列表
function queryOrderListByPage(page) {
	var orderSn = $("#orderSn").val();
	var orderStatus = $("#orderStatus").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var receiptAddress = $("#receiptAddress").val();
	$.ajax({
		type : "GET",
		url : "/order/queryOrderListByPage",
		dataType : "json",
		data : {
			"pageNum" : page,
			"orderSn" : orderSn,
			"orderStatus" : orderStatus,
			"startDate" : startDate,
			"endDate" : endDate,
			"receiptAddress" : receiptAddress
		},
		success : function(res) {
			if (res.result == "0") {
				var arry = new Array();
				var arryPage = new Array();
				var orderList = res.data.orderList;
				var pageInfo = res.data.pageInfo;
				setAjaxTbody(arry, pageInfo, orderList);
				setAjaxPage(arryPage, pageInfo, "queryOrderListByPage");
				$("#tbodyOrder").html(arry.toString());
				$("#pageOrder").html(arryPage.toString());
			} else {
				alertFailureInfo(data.msg);
			}
		},
		error : function() {
			alertFailureInfo("网络错误");
		}
	});
}

function setAjaxTbody(arry, pageInfo, list) {
	if (pageInfo != null) {
		q = (pageInfo.pageNum - 1) * pageInfo.pageSize;
	}
	// 使用each进行遍历
	$.each(list, function(n, obj) {
		arry.push("<tr><td style='border-top-style:none;border-right-style:none;border-left-style:none;border-bottom-style:none;'></td>" 
				+ "<td colspan=2 style='text-align:left;border-top-style:none;border-left-style:none;border-bottom-style:none;'>"
                + "<span style='color:blue;'>用户帐号：</span><span>"+obj.user.loginName+"</span></td>"
                + "<td rowspan=4>"+getorderStatusName(obj.orderInfo.orderStatus)+"</td><td rowspan=4>" 
                + getButton(obj.orderInfo.orderStatus,obj.orderInfo.id)+"</td></tr>");
		arry.push("<tr><td style='border-top-style:none;border-right-style:none;border-left-style:none;border-bottom-style:none;'></td>"
        		+ "<td style='text-align:left;border-top-style:none;border-right-style:none;border-left-style:none;border-bottom-style:none;'>"
                + "<span style='color:blue;'>订单号：</span><span>"+obj.orderInfo.orderSn+"</span></td>"
        		+ "<td style='text-align:left;border-top-style:none;border-left-style:none;border-bottom-style:none;'>"
                + "<span style='color:blue;'>下单时间：</span><span>"+getDateTime(obj.orderInfo.createTime)+"</span></td></tr>");
		arry.push("<tr><td style='border-top-style:none;border-right-style:none;border-left-style:none;border-bottom-style:none;'></td>"
				+ "<td colspan=2 style='text-align:left;border-top-style:none;border-left-style:none;border-bottom-style:none;'>"
				+getOrderDetailsHtml(obj.userOrderDetailsList)+"</td></tr>");
		arry.push("<tr><td style='border-top-style:none;border-right-style:none;border-left-style:none;'></td>"
				+ "<td style='text-align:left;border-top-style:none;border-left-style:none;border-right-style:none;'>"
                + "<span style='color:blue;'>订单价格：</span><span>"+obj.orderInfo.orderPrice+"</span></td>"
                + "<td style='text-align:left;border-top-style:none;border-left-style:none;'>"
                + "<span style='color:blue;'>收货地址：</span><span>"+obj.orderInfo.receiveAddress+"</span></td></tr>");
	});
}

function getorderStatusName(orderStatus){
	if(orderStatus=='buying'){
		return "等待采购";
	}else if(orderStatus=='alreadyBuy'){
		return "等待收货";
	}else if(orderStatus=='confirmReceived'){
		return "确认收货";
	}else if(orderStatus=='cancel'){
		return "已取消";
	}
}

function getButton(orderStatus,orderId){
	if(orderStatus=='buying'){
		return "<button class='but3' onclick=" + "alreadyBuy('"+ orderId + "');" + ">采购完成</button>" 
				+"<button class='but3' onclick=" + "cancelOrder('"+ orderId + "');" + ">取消订单</button>";
	}else if(orderStatus=='alreadyBuy'){
		return "<button class='but3' onclick=" + "confirmReceived('"+ orderId + "');" + ">确认收货</button>"
				+"<button class='but3' onclick=" + "cancelOrder('"+ orderId + "');" + ">取消订单</button>";
	}
	else if(orderStatus=='confirmReceived'){
		/*return "<button class='but3' onclick=" + "cancelOrder('"+ orderId + "');" + ">取消订单</button>";*/
		return "";
	}
	else if(orderStatus=='cancel'){
		return "";
	}
	
}
function getOrderDetailsHtml(userOrderDetailsList){
	var html = "";
	$.each(userOrderDetailsList, function(n, obj) {
		html +="<div><span>"+obj.goodsName+"</span><span>&nbsp;&nbsp;"+obj.goodsPrice+"</span><span>&nbsp×&nbsp;"+obj.num+"</span></div>";
	});
	return html;
}

function alreadyBuy(orderId){
	$.ajax({
		type : "GET",
		url : "/order/confirmUserOrderAlreadyBuyById",
		dataType : "json",
		data : {
			"orderId" : orderId
		},
		success : function(res) {
			if (res.result == "0") {
				alertSuccInfo("操作成功", queryOrderListByPage(1));
			} else {
				alertFailureInfo(res.data.msg);
			}
		},
		error : function() {
			alertFailureInfo("网络错误");
		}
	});
}
function confirmReceived(orderId){
	$.ajax({
		type : "GET",
		url : "/order/confirmUserOrderReceivedById",
		dataType : "json",
		data : {
			"orderId" : orderId
		},
		success : function(res) {
			if (res.result == "0") {
				alertSuccInfo("操作成功", queryOrderListByPage(1));
			} else {
				alertFailureInfo(res.data.msg);
			}
		},
		error : function() {
			alertFailureInfo("网络错误");
		}
	});
}
function cancelOrder(orderId){
	$.ajax({
		type : "GET",
		url : "/order/cancelUserOrderById",
		dataType : "json",
		data : {
			"orderId" : orderId
		},
		success : function(res) {
			if (res.result == "0") {
				alertSuccInfo("成功取消订单", queryOrderListByPage(1));
			} else {
				alertFailureInfo(res.data.msg);
			}
		},
		error : function() {
			alertFailureInfo("网络错误");
		}
	});
}

function exportShoppingListExcel(){
	var orderStatus = $("#orderStatus").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var receiptAddress = $("#receiptAddress").val();
	if(startDate==null||startDate==""||endDate==null||endDate==""){
		alertSuccInfo("开始时间或结束时间不能为空");
	}else{
		window.location.href="/order/exportShoppingListExcel?orderStatus="+orderStatus+
		"&receiptAddress="+receiptAddress+"&startTime="+startDate+"&endTime="+endDate;
	}
}

function exportDeliveryListExcel(){
	var orderStatus = $("#orderStatus").val();
	var orderSn = $("#orderSn").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var receiptAddress = $("#receiptAddress").val();
	if(startDate==null||startDate==""||endDate==null||endDate==""){
		alertSuccInfo("开始时间或结束时间不能为空");
	}else{
		window.location.href="/order/exportDeliveryListExcel?orderStatus="+orderStatus+
		"&orderSn="+orderSn+"&startTime="+startDate+"&endTime="+endDate+"&receiptAddress="+receiptAddress;
	}
	
}

/**
 * 复选框选择效果
 */
function selectCheckbox() {
	
    selectCheckboxJS("checkbox", "checkboxAll");
}

/**
 * 复选框全选效果
 */
function selectCheckboxAll() {
    
    selectCheckboxAllJS("checkbox", "checkboxAll");
}