$(document).ready(function() {
	queryTeamGroupByPage(1);
});

$("#searchOrder").click(function() {
	queryTeamGroupByPage(1);
});

// 分页查询拼团列表
function queryTeamGroupByPage(page) {
	var memberName = $("#memberName").val();
	var teamGroupSn = $("#teamGroupSn").val();
	var goodsName = $("#goodsName").val();
	var teamStatus = $("#teamStatus").val();
	
	var createStartDate = $("#createStartDate").val();
	var createEndDate = $("#createEndDate").val();
	
	var successStartDate = $("#successStartDate").val();
	var successEndDate = $("#successEndDate").val();
	
	$.ajax({
		type : "GET",
		url : "/teamGroup/queryTeamGroupByPage",
		dataType : "json",
		data : {
			"pageNum" : page,
			"memberName" : memberName,
			"teamGroupSn" : teamGroupSn,
			"goodsName" : goodsName,
			"teamStatus" : teamStatus,
			"createStartDate" : createStartDate,
			"createEndDate" : createEndDate,
			"successStartDate" : successStartDate,
			"successEndDate" : successEndDate
		},
		success : function(res) {
			if (res.result == "0") {
				var arry = new Array();
				var arryPage = new Array();
				var teamGroupList = res.data.teamGroupList;
				var pageInfo = res.data.pageInfo;
				setAjaxTbody(arry, pageInfo, teamGroupList);
				setAjaxPage(arryPage, pageInfo, "queryTeamGroupByPage");
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


$("#exportExcel").click(function() {
	exportExcel();
});


//导出拼团列表
function exportExcel() {
	var memberName = $("#memberName").val();
	var teamGroupSn = $("#teamGroupSn").val();
	var goodsName = $("#goodsName").val();
	var teamStatus = $("#teamStatus").val();
	
	var createStartDate = $("#createStartDate").val();
	var createEndDate = $("#createEndDate").val();
	
	var successStartDate = $("#successStartDate").val();
	var successEndDate = $("#successEndDate").val();
	
	window.location.href="/teamGroup/exportTeamGroupExcel?memberName="+memberName+
	"&teamGroupSn="+teamGroupSn+"&goodsName="+goodsName+"&teamStatus="+teamStatus+"&createStartDate="+createStartDate+
	"&createEndDate="+createEndDate+"&successStartDate="+successStartDate+"&successEndDate="+successEndDate;
	
	
}


function setAjaxTbody(arry, pageInfo, list) {
	if (pageInfo != null) {
		q = (pageInfo.pageNum - 1) * pageInfo.pageSize;
	}
	
	if(list.length==0){
		arry.push("<tr>"+ "<td style='text-align:center;'colspan='8'>"+"未查询到相关记录!"+"</td></tr>");
	}else{
		// 使用each进行遍历
		$.each(list, function(n, obj) {
				arry.push("<tr>"
						+ "<td style='text-align:center;'>"+obj.loginName+"</td>" 
						+ "<td style='text-align:center;'>"+obj.teamGroupSn+"</td>"
						+ "<td style='text-align:center;'>"+obj.goodName+"</td>"
						+ "<td style='text-align:center;'>"+obj.goodsPrice+"</td>"
						+ "<td style='text-align:center;'>"+getorderStatusName(obj.teamStatus)+"</td>"
						+ "<td style='text-align:center;'>"+getTimeShow(getDateTime(obj.createTime),getDateTime(obj.finishedTime))+"</td>"
						+ "<td style='text-align:left;'>"+obj.receiveAddress+"</td>"
						+ "<td style='text-align:center;'>"+getButton(obj.teamId,obj.userId,obj.teamStatus)+"</td>"
						+ "</tr>");	
		});
	}
}


function getTimeShow(createTime,finishedTime){   
	if(finishedTime == '' || finishedTime == null){
		return "<div><span>拼团时间:"+createTime+"</span></div>";
	}else{
		return "<div><span>拼团时间:"+createTime+"</span></div><div><span>成团时间:"+finishedTime+"</span></div>";
	}
}

function getorderStatusName(teamStatus){
	if(teamStatus=='0'){
		return "待支付";
	}else if(teamStatus=='1'){
		return "拼团中";
	}else if(teamStatus=='2'){
		return "已成团(采购中)";
	}else if(teamStatus=='3'){
		return "拼团失败";
	}else if(teamStatus=='4'){
		return "拼团取消";
	}else if(teamStatus=='5'){
		return "送货中";
	}else if(teamStatus=='6'){
		return "确认收货";
	}
}

function getButton(teamId,userId,teamStatus){
	if(teamStatus=='1'){
		return "<button class='but3' onclick=" + "updateStatus("+teamId+","+userId+"," +teamStatus+");" + ">取消订单</button>";
	}
	else if(teamStatus=='2'){
		return "<button class='but3' onclick=" + "updateStatus("+teamId+","+userId+"," +teamStatus+");" + ">正在配送</button>";
	}
	else if(teamStatus=='5'){
		return "<button class='but3' onclick=" + "updateStatus("+teamId+","+userId+"," +teamStatus+");" + ">确认收货</button>";
	}
	else{
		return "";
	}
}


function updateStatus(teamId,userId,teamStatus){
	$.ajax({
		type : "GET",
		url : "/teamGroup/updateTeamGroupByStatus",
		dataType : "json",
		data : {
			"teamId" : teamId,
			"userId" : userId,
			"teamStatus" : teamStatus
		},
		success : function(res) {
			if (res.result == "0") {
				alertSuccInfo(res.msg, queryTeamGroupByPage(1));
			} else {
				alertFailureInfo(res.msg);
			}
		},
		error : function() {
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