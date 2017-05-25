$(document).ready(function() {
	queryGoodsCategoryListByPage(1);
});

$("#searchGoodsCategory").click(function() {
	queryGoodsCategoryListByPage(1);
});

$("#addGoodsCategory").click(function(){
	addGoodsCategory();
});
// 分页查询管理员列表
function queryGoodsCategoryListByPage(page) {
	$.ajax({
		type : "GET",
		url : "/goodsCategory/queryGoodsCategoryListByPage",
		dataType : "json",
		data : {
			"pageNum" : page
		},
		success : function(res) {
			if (res.result == "0") {
				var arry = new Array();
				var arryPage = new Array();
				var goodsCategoryList = res.data.goodsCategoryList;
				var pageInfo = res.data.pageInfo;
				setAjaxTbody(arry, pageInfo, goodsCategoryList);
				setAjaxPage(arryPage, pageInfo, "queryGoodsCategoryListByPage");
				$("#tbodyGoodsCategory").html(arry.toString());
				$("#pageGoodsCategory").html(arryPage.toString());
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
		arry.push("<td>" + obj.categoryName + "</td>" 
				+ "<td><button class='but3' onclick=" + "updateGoodsCategory('"
				+ obj.id + "','" + obj.categoryName + "');" + ">修改</button>"+ "<button class='but3' onclick=" + "confirmDelete('"
				+ obj.id + "');" + ">删除</button></td>" 
				+ "</tr>");
	});
}

function addGoodsCategory(){
	$('#myModal').modal('show');
	$("#goodsCategoryId").val("");
	$("#goodsCategoryInput").val("");
}
function updateGoodsCategory(goodsCategoryId,categoryName){
	$('#myModal').modal('show');
	$("#goodsCategoryId").val(goodsCategoryId);
	$("#goodsCategoryInput").val(categoryName);
}

function goodsCategoryConfrimButton(){
	var goodsCategoryId = $("#goodsCategoryId").val();
	var goodsCategoryInput = $("#goodsCategoryInput").val();
	if(goodsCategoryId!=null&&goodsCategoryId!=""){
		 $.ajax({
	         type: "GET",
	         url: "/goodsCategory/updateGoodsFirstCategory",
	         dataType: "json",
	         data: {
	             "goodsCategoryId" : goodsCategoryId,
	             "categoryName" : goodsCategoryInput
	         },
	         success: function (res) {
	             if (res.result == "0") {
	            	 $('#myModal').modal('hide');
	            	 $("#goodsCategoryInput").val="";
	            	 $("#errMsg").text("");
	            	 alertSuccInfo("修改成功",queryGoodsCategoryListByPage(1));
	             } else{
	            	 $("#errMsg").text(res.msg);
	             }
	         },
	         error: function () {
	         	alertFailureInfo("网络错误");
	         }
	     });
	}else{
		$.ajax({
	         type: "GET",
	         url: "/goodsCategory/addGoodsFirstCategory",
	         dataType: "json",
	         data: {
	             "categoryName" : goodsCategoryInput
	         },
	         success: function (res) {
	             if (res.result == "0") {
	            	 $('#myModal').modal('hide');
	            	 $("#goodsCategoryInput").val="";
	            	 $("#errMsg").text("");
	            	 alertSuccInfo("添加成功",queryGoodsCategoryListByPage(1));
	             } else{
	            	 $("#errMsg").text(res.msg);
	             }
	         },
	         error: function () {
	         	alertFailureInfo("网络错误");
	         }
	     });
	}
}

/**
 * 删除记录
 */
function confirmDelete(goodsCategoryId){
	
	alertPromptInfo2("确认删除么？", deleteGoodsCategory, goodsCategoryId, null, null);
}

function deleteGoodsCategory(goodsCategoryId){
		$.ajax({
	        type: "GET",
	        url: "/goodsCategory/deleteGoodsFirstCategoryById",
	        dataType: "json",
	        data: {
	            "goodsCategoryId" : goodsCategoryId
	        },
	        success: function (res) {
	            if (res.result == "0") {
	           	 alertSuccInfo("删除成功",queryGoodsCategoryListByPage(1));
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