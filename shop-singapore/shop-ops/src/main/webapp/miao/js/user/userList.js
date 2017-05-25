$(document).ready(function() {
	queryUserListByPage(1);
});

$("#searchUser").click(function() {
	queryUserListByPage(1);
});

// 分页查询用户列表
function queryUserListByPage(page) {
	var loginName = $("#loginName").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	$.ajax({
		type : "GET",
		url : "/user/queryUserListByPage",
		dataType : "json",
		data : {
			"pageNum" : page,
			"loginName" : loginName,
			"startDate" : startDate,
			"endDate" : endDate
		},
		success : function(res) {
			if (res.result == "0") {
				var arry = new Array();
				var arryPage = new Array();
				var userList = res.data.userList;
				var pageInfo = res.data.pageInfo;
				setAjaxTbody(arry, pageInfo, userList);
				setAjaxPage(arryPage, pageInfo, "queryUserListByPage");
				$("#tbodyUser").html(arry.toString());
				$("#pageUser").html(arryPage.toString());
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
		arry.push("<tr><td><input type='checkbox' name='checkbox' onclick='selectCheckbox();' value="
				+ obj.id + "></td>");
		arry.push("<td>" + obj.loginName + "</td>" 
				+ "<td>" + obj.nickName +"</td>" 
				+ "<td>" + getDateTime(obj.createTime) + "</td>" 
				+ "<td><button class='but' onclick=" + "getUserReceiptAddress('"
				+ obj.id + "');" + ">查看用户收货地址</button>"+ "</td>" 
				+ "</tr>");
	});
}

function getUserReceiptAddress(userId){
	$('#myModal').modal('show');
	 $.ajax({
         type: "GET",
         url: "/user/findUserReceiptAddress",
         dataType: "json",
         data: {
             "userId": userId
         },
         success: function (res) {
             if (res.result == "0") {
            	 var userReceiptAddressList = res.data.userReceiptAddressList;
            	 var userReceiptAddressArr = new Array();
            	 $.each(userReceiptAddressList, function(n, obj) {
            		 if(n == 0){
            			 userReceiptAddressArr.push("<span>"+obj.receiptAddress+"</span><span style='color:red;'>(默认)</span>");
            		 }else{
            			 userReceiptAddressArr.push("<hr><div style='text-align:left;'><span>"+obj.receiptAddress+"</span></div>");
            		 }
            	 });
            	 $("#userReceiptAddressDiv").html(userReceiptAddressArr);
             } else{
            	 alertFailureInfo(res.msg);
             }
         },
         error: function () {
         	alertFailureInfo("网络错误");
         }
     });
}

function addressConfrimButton(){
	$('#myModal').modal('hide');
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