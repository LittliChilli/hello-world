document.write("<script src='/miao/js/common/paginator_ajax.js'><\/script>");
document.write("<script src='/miao/js/common/common_tip.js'></script>");

$(function() {

	
var categoryValue = $("#categoryselectorId").val();
$("#categoryselector option[value='"+categoryValue+"']").attr("selected",true);


var goodsTypeValue = $("#goodsTypeselectorId").val();
$("#goodsTypeselector option[value='"+goodsTypeValue+"']").attr("selected",true);

if(goodsTypeValue == "1"){
	$("#teamGroupNumDiv").hide();
	$("#teamGroupTimeDiv").hide();
}


	$("#uploadsubmit").click(function () {
	    $("#horizontal-form").validate(
	        {
	            rules: {
	            	goodsName: {
	                    required: true
	                },
	                category: {
	                    required: true
	                },
	                goodsPrice: {
	                    required: true,
	                    number: true
	                },
	                goodsSoldNumber: {
	                    required: false,
	                    number: true
	                },
	                teamGroupNum: {
                        required: true,
                        number: true,
                        min: 2
                    },
                    teamGroupTime: {
                        required: true,
                        number: true,
                        min: 1
                    }
	            },
	            messages: {
	            	goodsName: {
	                    required: "请填写商品名称"
	                },
	                category: {
	                    required: "请选择商品类别"
	                },
	                goodsPrice: {
	                    required: "请填写商品价格"
	                },
	                goodsSoldNumber: {
	                	required: "请填写合法的数字"
	                },
                    teamGroupNum: {
                    	required: "请填写数字",
                    	min: "拼团人数不能小于2个人哦"
                    },
                    teamGroupTime: {
                    	required: "请填写数字,单位：小时",
                    	min: "有效拼团时间至少大于1个小时哦"
                    }
	            },
	            submitHandler: function (form) {
	            	var formDate = new FormData(document.getElementById("horizontal-form"));
	                // 先做判断，看看是不是活动专区项目，再决定怎样传值
	                $.ajax({
	                    type: "POST",
	                    url: "/goods/addGoods",
	                    dataType: "json",
	                    data: formDate,
	                    contentType: false,
	                    processData: false, 
	                    success: function (data) {
	                        if (data.result == "0") {
	                        	alertSuccInfo(data.msg,gotoGoodsList);
	                        } else {
	                        	alertFailureInfo(data.msg);
	                        }
	                    },
	                    error: function () {
	                    	alertFailureInfo("网络错误");
	                    }
	                });
	            }
	        });
	});
});


//商品图片预览
function imgClick(num){
	$("#goodsPic"+num).click();
}

function imgChange(num){
	picChange($("#goodsPic"+num), $("#goodsPicY"+num));
	var fileNamePath = $("#goodsPic"+num).val();
	var uploadFileName = getUploadFileName(fileNamePath);
	if (uploadFileName != null && uploadFileName != "") {
		$("#goodsImg"+num).val(uploadFileName);
	} else {
		$("goodsImg"+num).val($("#goodsPicName"+num).val());
	}
}

//商品详情图片预览
function goodsDetailsClick(num){
	$("#goodsDetailsPic"+num).click();
}

function goodsDetailsChange(num){
	picChange($("#goodsDetailsPic"+num), $("#goodsDetailsPicY"+num));
	var fileNamePath = $("#goodsDetailsPic"+num).val();
	var uploadFileName = getUploadFileName(fileNamePath);
	if (uploadFileName != null && uploadFileName != "") {
		$("#goodsDetailsImg"+num).val(uploadFileName);
	} else {
		$("goodsDetailsImg"+num).val($("#goodsDetailsPicName"+num).val());
	}
}

function gotoGoodsList(){
	
	window.location.href = "/goods/toGoodsList";
}

function ifShowDiv(){
	var goodsTypeValue = $("#goodsTypeselector").val();
	if(goodsTypeValue == "1"){
		$("#teamGroupNumDiv").hide();
		$("#teamGroupTimeDiv").hide();
	}
	else{
		$("#teamGroupNumDiv").show();
		$("#teamGroupTimeDiv").show();
	}
}
