var pciUrlForEmpty = "/miao/imgs/iconfont-shangchuantupian.png";

/**
 * 操作成功信息弹出框
 */
function alertSuccInfo(msg) {

	var message = "操作成功";

	if (msg != null && msg != "" && msg != undefined) {
		message = msg;
	}

	alertInfo("succes.png", message);
}

/**
 * 操作成功信息弹出框,带OK按钮功能
 */
function alertSuccInfo(msg, okClickMethod) {
	
	var message = "操作成功";

	if (msg != null && msg != "" && msg != undefined) {
		message = msg;
	}
	
	alertInfo2("succes.png", message, "确认", "取消", okClickMethod, null, null, null , null, null);
}

/**
 * 提示信息弹出框
 * 
 * @param msg
 */
function alertPromptInfo(msg) {

	var message = "提示信息";

	if (msg != null && msg != "" && msg != undefined) {
		message = msg;
	}

	alertInfo("tipinfo.png", message);
}

/**
 * 提示信息弹出框带确认和取消按钮
 * 
 * @param msg
 * @param okClickMethod
 * @param failureClickMethod
 */
function alertPromptInfo2(msg, okClickMethod, okData, failureClickMethod, failData) {

	var message = "提示信息";
	
	if (msg != null && msg != "" && msg != undefined) {
		message = msg;
	}

	alertInfo2("tipinfo.png", message, "确认", "取消", okClickMethod,
			failureClickMethod, null, okData, failData, null);
}

/**
 * 提示信息弹出框带确认和取消按钮
 * 
 * @param msg
 * @param okClickMethod
 * @param failureClickMethod
 */
function alertPromptInfo3(msg, okClickMethod, failureClickMethod) {

	var message = "提示信息";
	
	if (msg != null && msg != "" && msg != undefined) {
		message = msg;
	}

	alertInfo2("tipinfo.png", message, "确认", "取消", okClickMethod,
			failureClickMethod, null, null, null, null);
}

/**
 * 取消空方法操作
 */
function cancleOperation() {
	
}

/**
 * 操作失败信息弹出框
 */
function alertFailureInfo(msg) {

	var message = "操作失败";

	if (msg != null && msg != "" && msg != undefined) {
		message = msg;
	}

	alertInfo("failure.png", message);
}

/**
 * 信息弹出框
 */

function alertInfo(img, msg) {
	showMessage(img, msg);

	displayTip();
}

/**
 * 信息弹出框2
 */
function alertInfo2(img, msg, okButName, failureButName, okClickMethod,
		failureClickMethod, cancleMethod, okData, failData, cancleData) {

	showMessage2(img, msg, okButName, failureButName, okClickMethod,
			failureClickMethod, cancleMethod, okData, failData, cancleData);
	
	displayTip();
}

/**
 * 弹出框实现
 * 
 * @param img
 * @param msg
 */
function showMessage(img, msg) {

	$("#tipInfomation").empty();

	var arry = new Array();

	arry
			.push("<div class='bg'></div>"
					+ "<div class='content modal-content' style='width:400px;height:200px;'>"
					+ "<div class='title-text foat-left modal-header' style='width:370px;height:50px;'><h2>提示信息</h2></div>"
					+ "<div class='title-img' style='width:400px;'><a href='#' onclick='tipClose();'><img src='/miao/imgs/close.png'></a></div>"
					+ "<div class='hint foat-left' style='height:100px;'>"
					+ "<div class='hint-img foat-left' style='margin-top:0px'><img src='/miao/imgs/"
					+ img
					+ "'></div><span style='font-size:16px'>"
					+ msg
					+ "</span></div>"
					+ "<div style='text-align:center;'>"
					+ "<button class='btn-success btn' onclick='tipConfirm();'>确认</button>"
					+ "</div></div>");

	$("#tipInfomation").html(arry.toString());
}

/**
 * 弹出框实现2
 */
function showMessage2(img, msg, okButName, failureButName, okClickMethod,
		failureClickMethod, cancleMethod, okData, failData, cancleData) {

	$("#tipInfomation").empty();
	
	var contentArray = "<div class='bg'></div>"
			+ "<div class='content modal-content' style='width:400px;height:200px;'>"
			+ "<div class='title-text foat-left modal-header' style='width:370px;height:50px;'><h2>提示信息</h2></div>"
			+ "<div class='title-img' style='width:400px;'><a href='#' onclick='tipClose("
			+ cancleMethod +","+ cancleData
			+ ");'><img src='/miao/imgs/close.png'></a></div>"
			+ "<div class='hint foat-left' style='height:100px;'>"
			+ "<div class='hint-img foat-left' style='margin-top:0px'><img src='/miao/imgs/"
			+ img + "'></div>" + "<span style='font-size:16px'>" + msg
			+ "</span></div>" + "<div style='text-align:center;'>"
			+ "<button class='btn-success btn' onclick='tipConfirm("
			+ okClickMethod + "," + okData + ");'>" + okButName
			+ "</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp";

	if (failureClickMethod != null || failureClickMethod != undefined) {
		contentArray += "<button class='btn-success btn' onclick='tipCancle("
				+ failureClickMethod + "," + failData +  ");'>" + failureButName + "</button>";
	}
	
	contentArray += "</div></div>";

	$("#tipInfomation").html(contentArray);
}

/**
 * 取消操作
 */
function tipClose(functionName, data) {

	hiddenTip();

	functionApply(functionName, data);
}

/**
 * 确认按钮操作
 */
function tipConfirm(functionName, data) {

	hiddenTip();

	functionApply(functionName, data);
}

/**
 * 取消按钮操作
 */
function tipCancle(functionName, data) {
	hiddenTip();

	functionApply(functionName, data);
}

/**
 * 隐藏弹出框
 */
function hiddenTip() {
	jQuery('.bg').fadeOut(200);
	jQuery('.content').fadeOut(400);
}

/**
 * 显示弹出框
 */
function displayTip() {
	jQuery('.bg').fadeIn(200);
	jQuery('.content').fadeIn(400);
}

/**
 * 判断是一个function,并执行
 * 
 * @param functionName
 */
function functionApply(functionName, data) {
	
	if (data == null) {
		if (typeof (functionName) === 'function') {
			functionName.apply(this);
		} else {
			var functionNameExt = eval(functionName);
			
			if (typeof (functionNameExt) === 'function') {
				functionNameExt.apply(this);
			}
		}
	} else {
		
		if (typeof (functionName) === 'function') {
			functionName.apply(this, [data]);
		} else {
			var functionNameExt = eval(functionName);
			
			if (typeof (functionNameExt) === 'function') {
				functionNameExt.apply(this, [data]);
			}
		}
	}
}

/**
 * 预览图片替换
 * 
 * @param hospic
 */
function picChange(file, imgObj) {
	var fileObj = file[0];
	var windowURL = window.URL || window.webkitURL;
	if (fileObj && fileObj.files && fileObj.files[0]) {
		imgObj.attr('src', windowURL.createObjectURL(fileObj.files[0]));
	} else {
		imgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
		imgObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = file
				.val();
	}
}

/**
 * 上传图片
 */
function userInfoUploadImg(formData, imgpicForm, imgpicStr, imgpic) {

	$.ajax({
		url : imgpicForm.attr('action'),
		type : "POST",
		data : formData,
		processData : false, // 告诉jQuery不要去处理发送的数据
		contentType : false, // 告诉jQuery不要去设置Content-Type请求头
		success : function(data2) {
			if (data2.result == "0") {
				imgpicStr.val(data2.data.imgUrl);
				imgpic.attr('src', data2.data.imgPath);
			} else {
				alertFailureInfo(data2.msg);
			}
		},
		error : function() {
			alertFailureInfo("网络错误");
		}
	});
}

/**
 * 传入上传文件全路径,返回文件名称
 * 
 * @param fileNamePath
 * @returns {String}
 */
function getUploadFileName(fileNamePath) {

	var fileName = "";

	if (fileNamePath != "") {
		fileName = fileNamePath.substring(fileNamePath.lastIndexOf("\\") + 1);
	}

	return fileName;
}

/**
 * 传入上传文件全路径,返回文件名称后缀
 * 
 * @param fileNamePath
 * @returns {String}
 */
function getUploadFileNameSuffix(fileNamePath) {

	var fileNameSuffix = "";

	if (fileNamePath != "") {
		fileNameSuffix = fileNamePath
				.substring(fileNamePath.lastIndexOf(".") + 1);
	}

	return fileNameSuffix;
}

/**
 * 后台接收文件全路径,返回文件名称
 * 
 * @param fileNamePath
 * @returns {String}
 */
function getMmfqFileName(fileNamePath) {

	var fileName = "";

	if (fileNamePath != "" && fileNamePath != null) {
		fileName = fileNamePath.substring(fileNamePath.lastIndexOf("/") + 1);
	}

	return fileName;
}

/**
 * 转换时间格式
 * 
 * @param date
 * @returns
 */
function getDateTime(date) {
	if (date) {
		return (new Date(date)).Format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
	}
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

// 查询时间校验
function checkData(startTime, endTime) {
	var startTime = $("#" + startTime).val().replace(/-/g, '');
	var endTime = $("#" + endTime).val().replace(/-/g, '');
	if (startTime == null || startTime == '') {
		alertFailureInfo("开始时间不能为空");
		return false;
	}
	if (endTime == null || endTime == '') {
		alertFailureInfo("结束时间不能为空");
		return false;
	}
	startTime = parseInt(startTime);
	endTime = parseInt(endTime);
	if (startTime > endTime) {
		alertFailureInfo("开始时间不能大于结束时间");
		return false;
	}
	var tody = new Date();
	year = tody.getFullYear();
	month = tody.getMonth() + 1;
	day = tody.getDate();
	month = month < 10 ? "0" + month : month
	day = day < 10 ? "0" + day : day
	var now = (year + "" + month + "" + day);
	now = parseInt(now);
	if (endTime > now) {
		alertFailureInfo("结束时间不能大于当前时间");
		return false;
	}
	return true;
}

/**
 * 获取省份信息
 */
function getAllProv(proidText) {
	var obj = document.getElementById("proId");
	obj.options.length = 0;
	$.ajax({
		url : "/getCommonData/getProv",
		type : "POST",
		success : function(data) {
			if (data.result == "0") {
				obj.options.add(new Option("全部", ""));
				$.each(data.data.provList, function(n, prov) {
					obj.options.add(new Option(prov.proname, prov.proid));
				});
				jsSelectItemByValue(obj, proidText);
			}
		},
		error : function() {
			alertFailureInfo("网络错误");
		}
	});
}

/**
 * 获取城市信息
 */
function getAllCity(cityText, proidText) {
	if (proidText == "" || proidText == null) {
		cityText = "";
	}

	var obj = document.getElementById("cityId");
	obj.options.length = 0;
	$.ajax({
		url : "/getCommonData/getCity",
		type : "POST",
		dataType : "json",
		data : "provId=" + proidText,
		success : function(data) {
			if (data.result == "0") {
				obj.options.add(new Option("全部", ""));
				$.each(data.data.cityList, function(n, city) {
					obj.options.add(new Option(city.cityname, city.cityid));
				});
				jsSelectItemByValue(obj, cityText);
			}
		},
		error : function() {
			alertFailureInfo("网络错误");
		}
	});
}

function jsSelectItemByValue(objSelect, objItemText) {
	for (var i = 0; i < objSelect.options.length; i++) {
		if (objSelect.options[i].value == objItemText) {
			objSelect.options[i].selected = true;
			break;
		}
	}
}

/**
 * 根据城市获取商品信息
 */
function getStoreGoodsByCity(cityId, goodsIdText) {
	var cityId = $("#" + cityId + "").val();
	if (cityId == null) {
		cityId = "";
	}
	var goodsText = $("#" + goodsIdText + "").val();
	if (goodsText == null) {
		goodsText = "";
	}
	var obj = document.getElementById("goodsId");
	obj.options.length = 0;
	$.ajax({
		url : "/goodsShelfController/getGoodsByCity",
		type : "POST",
		dataType : "json",
		data : "cityId=" + cityId,
		success : function(data) {
			if (data.result == "0") {
				obj.options.add(new Option("全部", ""));
				$.each(data.data.goodsList, function(n, goods) {
					obj.options.add(new Option(goods.goodsName, goods.id));
				});
				jsSelectItemByValue(obj, goodsText);
			}
		},
		error : function() {
			alertFailureInfo("网络错误");
		}
	});
}

/**
 * 获取优惠方案信息
 */
function getDiscountPlan(discountPlanId, discountPlanIdText) {
	var discountPlanText = $("#" + discountPlanIdText + "").val();
	if (discountPlanText == null) {
		discountPlanText = "";
	}
	var obj = document.getElementById(discountPlanId);
	obj.options.length = 0;
	$.ajax({
		url : "/goodsShelfController/getDiscountPlan",
		type : "POST",
		dataType : "json",
		data : {},
		success : function(data) {
			if (data.result == "0") {
				obj.options.add(new Option("", ""));
				$.each(data.data.discountPlanList, function(n, discountPlan) {
					obj.options.add(new Option(discountPlan.discountPlanName,
							discountPlan.id));
				});
				jsSelectItemByValue(obj, discountPlanText);
			}
		},
		error : function() {
			alertFailureInfo("网络错误");
		}
	});
}

/**
 * 获取商品群组项目列表
 */
function getStoreGoodGroup(goodsGroupId, goodsGroupIdText){
	var goodsGroupText = $("#" + goodsGroupIdText + "").val(); 
	if(goodsGroupText == null){
		goodsGroupText = "";
	}
	var obj = document.getElementById(goodsGroupId);
	obj.options.length = 0;
	$.ajax({
		url : "/goodsShelfController/getGoodsGroup",
		type : "POST",
		dataType : "json",
		data : {},
		success : function(data) {
			if (data.result == "0") {
				obj.options.add(new Option("全部", ""));
				$.each(data.data.storeGoodsGroupList, function(n, storeGoodsGroup) {
					obj.options.add(new Option(storeGoodsGroup.storeGoodsGroupName,
							storeGoodsGroup.id));
				});
				jsSelectItemByValue(obj, goodsGroupText);
			}
		},
		error : function() {
			alertFailureInfo("网络错误");
		}
	});
}

/**
 * 复选框全选
 * @author lion
 * @date 2016年3月2日
 * @param id
 */
function checkAll(id){
	var obj = document.getElementById(id);
	if(obj.checked==true){
		var objs = document.getElementsByName("checkbox");
		for(var i=0;i<objs.length;i++){
			objs[i].checked=true;
		}
	}else{
		var objs = document.getElementsByName("checkbox");
		for(var i=0;i<objs.length;i++){
			objs[i].checked=false;
		}
	}
}



/**
 * 复选框选择效果
 */
function selectCheckboxJS(checkboxName, checkboxAllId) {
    
	var checkboxes = $("[name='"+checkboxName+"']");
    
    var checkboxAll = $("[name="+checkboxAllId+"]");
    
    var ischeckboxAll = true;
    
    for (var i = 0; i < checkboxes.length; i++) {
        var checkbox = checkboxes[i];
        if (checkbox.type == 'checkbox') {
            var td = checkbox.parentNode;
            var tr = td.parentNode;
            if (td.tagName == "TD") {
                if (checkbox.checked) {
                    tr.className = "selected-item";
                }
                else {
                    tr.className = "";
                    ischeckboxAll = false;
                }
            }
        }
    }
    
    if (ischeckboxAll) {
    	checkboxAll[0].checked = true;
    } else {
    	checkboxAll[0].checked = false;
    }
}

/**
 * 复选框全选效果
 */
function selectCheckboxAllJS(checkboxName, checkboxAllId) {
	
	var checkboxes = $("[name='"+checkboxName+"']");
    
    var checkboxAll = $("[name="+checkboxAllId+"]");
    
    if (checkboxAll[0].checked) {
        for (var i = 0; i < checkboxes.length; i++) {
            var checkbox = checkboxes[i];
            if (checkbox.type == 'checkbox') {
            	checkbox.checked = true;
            }
        }
    } else {
    	for (var i = 0; i < checkboxes.length; i++) {
            var checkbox = checkboxes[i];
            if (checkbox.type == 'checkbox') {
            	checkbox.checked = false;
            }
        }
    }
}

