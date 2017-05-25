$(document).ready(function() {
	queryAdminListByPage(1);
});

$("#searchAdmin").click(function() {
	queryAdminListByPage(1);
});

$("#addAdmin").click(function(){
	addAdmin();
});

// 分页查询管理员列表
function queryAdminListByPage(page) {
	var loginName = $("#loginName").val();
	$.ajax({
		type : "GET",
		url : "/admin/queryAdminListByPage",
		dataType : "json",
		data : {
			"pageNum" : page,
			"loginName" : loginName
		},
		success : function(res) {
			if (res.result == "0") {
				var arry = new Array();
				var arryPage = new Array();
				var adminList = res.data.adminList;
				var pageInfo = res.data.pageInfo;
				setAjaxTbody(arry, pageInfo, adminList);
				setAjaxPage(arryPage, pageInfo, "queryAdminListByPage");
				$("#tbodyAdmin").html(arry.toString());
				$("#pageAdmin").html(arryPage.toString());
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
		var nickName = obj.nickName;
		if(nickName == null || nickName == undefined){
			nickName = "";
		}
		arry.push("<tr><td><input type='checkbox' name='checkbox' onclick='selectCheckbox();' value="
				+ obj.id + "></td>");
		arry.push("<td>" + obj.loginName + "</td>" 
				+ "<td>" + nickName +"</td>" 
				+ "<td>" + getDateTime(obj.createTime) + "</td>" 
				+ "<td><button class='but3' onclick=" + "resetAdminPassward('"
				+ obj.id + "');" + ">重置密码</button>"+ "<button class='but3' onclick=" + "deleteAdminById('"
				+ obj.id + "');" + ">删除帐号</button></td>" 
				+ "</tr>");
	});
}

function addAdmin(){
	$('#myModal').modal('show');
}

function addAdminInfoConfrimButton(){
	var addLoginName = $("#addLoginName").val();
	var addLoginPassword = $("#addLoginPassword").val();
	var addNickName = $("#addNickName").val();
	 $.ajax({
         type: "GET",
         url: "/admin/addAdminInfo",
         dataType: "json",
         data: {
             "addLoginName" : addLoginName,
             "addLoginPassword" : addLoginPassword,
             "addNickName" : addNickName
         },
         success: function (res) {
             if (res.result == "0") {
            	 $('#myModal').modal('hide');
            	 $("#addLoginName").val="";
            	 $("#addLoginPassword").val="";
            	 $("#addNickName").val="";
            	 $("#loginNameErrMsg").text("");
            	 $("#loginPasswordErrMsg").text("");
            	 alertSuccInfo("新增成功",queryAdminListByPage(1));
             } else{
            	 if(res.result == "1"){
            		 $('#myModal').modal('hide');
            		 alertFailureInfo(res.msg);
            	 }else if(res.result == "2"){
            		 $("#loginPasswordErrMsg").text("");
            		 $("#loginNameErrMsg").text(res.msg);
            	 }else if(res.result == "3"){
            		 $("#loginNameErrMsg").text("");
            		 $("#loginPasswordErrMsg").text(res.msg);
            	 }
             }
         },
         error: function () {
         	alertFailureInfo("网络错误");
         }
     });
}

function resetAdminPassward(adminId){
	 $.ajax({
         type: "GET",
         url: "/admin/resetAdminPassward",
         dataType: "json",
         data: {
             "adminId": adminId
         },
         success: function (res) {
             if (res.result == "0") {
            	 alertSuccInfo("新密码为“111111”");
             } else{
            	 alertFailureInfo(res.msg);
             }
         },
         error: function () {
         	alertFailureInfo("网络错误");
         }
     });
}

function deleteAdminById(adminId){
	$.ajax({
        type: "GET",
        url: "/admin/deleteAdminInfo",
        dataType: "json",
        data: {
            "adminId": adminId
        },
        success: function (res) {
            if (res.result == "0") {
           	 alertSuccInfo("删除成功",queryAdminListByPage(1));
            } else{
           	 alertFailureInfo(res.msg);
            }
        },
        error: function () {
        	alertFailureInfo("网络错误");
        }
    });
}

function auditAdminById(adminId){
	$.ajax({
        type: "GET",
        url: "/admin/auditAdminById",
        dataType: "json",
        data: {
            "adminId": adminId
        },
        success: function (res) {
            if (res.result == "0") {
           	 alertSuccInfo("审核成功",queryAdminListByPage(1));
            } else{
           	 alertFailureInfo(res.msg);
            }
        },
        error: function () {
        	alertFailureInfo("网络错误");
        }
    });
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