jQuery(document).ready(function () {

    var p1 = false;
    var p2 = false;
    var p3 = false;

    $("#identityPic1").change(function () {
    	layer.open({
    	    type: 2,
    	    content: '上传中。。。',
    	    shadeClose:false
    	});
    	
        var url = $("#idPic1Form").attr("action");
        var fd = new FormData(document.getElementById("idPic1Form"));
        $.ajax({
            type: "POST",
            url: $("#idPic1Form").attr("action"),
            dataType: "json",
            enctype: 'multipart/form-data',
            data: fd,
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false,
            success: function (data1) {
                p1 = true;
                $("#ip1").attr("src", $.cookie("Identitypic1"));
                layer.closeAll('loading');
            },
            error: function () {
            	layer.closeAll('loading');
                alert("网络错误");
            }
        });
    });

    $("#identityPic2").change(function () {
    	layer.open({
    	    type: 2,
    	    content: '上传中。。。',
    	    shadeClose:false
    	});
    	
        var url = $("#idPic2Form").attr("action");
        var fd = new FormData(document.getElementById("idPic2Form"));
        $.ajax({
            type: "POST",
            url: $("#idPic2Form").attr("action"),
            dataType: "json",
            enctype: 'multipart/form-data',
            data: fd,
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false,
            success: function (data2) {
                p2 = true;
                $("#ip2").attr("src", $.cookie("Identitypic2"));
                layer.closeAll('loading');
            },
            error: function () {
            	layer.closeAll('loading');
                alert("网络错误");
            }
        });
    });

    $("#identityPic3").change(function () {
    	layer.open({
    	    type: 2,
    	    content: '上传中。。。',
    	    shadeClose:false
    	});
    	
        var url = $("#idPic3Form").attr("action");
        var fd = new FormData(document.getElementById("idPic3Form"));
        $.ajax({
            type: "POST",
            url: $("#idPic3Form").attr("action"),
            dataType: "json",
            enctype: 'multipart/form-data',
            data: fd,
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false,
            success: function (data3) {
                p3 = true;
                $("#ip3").attr("src", $.cookie("Identitypic3"));
                layer.closeAll('loading');
            },
            error: function () {
            	layer.closeAll('loading');
                alert("网络错误");
            }
        });
    });

    jQuery("#toStudentPic").click(function () {
        if (p1 == true && p2 == true && p3 == true) {
            window.location.href = "/managerCredit/tostudentpicup";
        } else {
//            layer.msg("请将照片信息补充完整");
        	layer.open({
	        	    shade:false,
	        	    content: "请将照片信息补充完整",
	        	    style: 'background-color: rgba(0,0,0,.5);; color:#fff; border:none;opacity:0.2;text-align: center;',
	        	    time: 2
	        	});
        }
    });
})