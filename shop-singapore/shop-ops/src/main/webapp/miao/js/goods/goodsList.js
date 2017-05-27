$(document).ready(function() {
	queryGoodsListByPage(1);
});

$("#searchGoods").click(function() {
	queryGoodsListByPage(1);
});

$("#uploadGoods").click(function() {
	window.open("/goods/toUploadGoods?");
});

// 分页查询用户列表
function queryGoodsListByPage(page) {
	var goodsName = $("#goodsName").val();
	var goodsTypeId = $("#goodsType").val();
	$.ajax({
		type : "GET",
		url : "/goods/queryGoodsListByPage",
		dataType : "json",
		data : {
			"pageNum" : page,
			"goodsName" : goodsName,
			"goodsTypeId" : goodsTypeId
		},
		success : function(res) {
			if (res.result == "0") {
				var arry = new Array();
				var arryPage = new Array();
				var goodsList = res.data.goodsList;
				var pageInfo = res.data.pageInfo;
				setAjaxTbody(arry, pageInfo, goodsList);
				setAjaxPage(arryPage, pageInfo, "queryGoodsListByPage");
				$("#tbodyGoods").html(arry.toString());
				$("#pageGoods").html(arryPage.toString());
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
		arry.push("<td>" + obj.goodsSn + "</td>" 
				+ "<td>" + obj.goodsName +"</td>" 
				+ "<td>" + obj.goodsPrice + "</td>" 
				+ "<td>" + obj.goodsSoldNumber + "</td>" 
				+ "<td><button class='but' onclick=" + "updateGoods('"
				+ obj.id + "');" + ">修改商品</button>"+ "<button class='but' onclick=" + "confirmDelete('"
				+ obj.id + "');" + ">删除商品</button></td>" 
				+ "</tr>");
	});
}


function updateGoods(goodsId) {
	window.open('/goods/goodsDetail?goodsId='+goodsId);
}

/**
 * 删除记录
 */
function confirmDelete(goodsId){
	alertPromptInfo2("确认删除么？", deleteGoods, goodsId, null, null);
	
}

function deleteGoods(goodsId){
		  $.ajax({
	          type: "GET",
	          url: "/goods/deleteGoodsById",
	          dataType: "json",
	          data : {
	  			"goodsId" : goodsId
	  		},
	          success: function (data) {
	              if (data.result == "0") {
	              	alertSuccInfo(data.msg,queryGoodsListByPage(1));
	              } else {
	              	alertFailureInfo(data.msg);
	              }
	          },
	          error: function () {
	          	alertFailureInfo("网络错误");
	          }
	      });
}
