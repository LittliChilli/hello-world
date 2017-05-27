jQuery(document).ready(function () {
    var s1 = false;
    var s2 = false;
    var s3 = false;
    var s4 = false;
    var s5 = false;
    
    var p1 = false;
    var p2 = false;

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

    $("#studentCard").change(function () {
    	layer.open({
    	    type: 2,
    	    content: '上传中。。。',
    	    shadeClose:false
    	});
    	
        var url = $("#sPic1Form").attr("action");
        var fd = new FormData(document.getElementById("sPic1Form"));
        $.ajax({
            type: "POST",
            url: $("#sPic1Form").attr("action"),
            dataType: "json",
            enctype: 'multipart/form-data',
            data: fd,
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false,
            success: function (data1) {
                s1 = true;
                $("#s1").attr("src", $.cookie("student1"));
                layer.closeAll('loading');
            },
            error: function () {
            	layer.closeAll('loading');
                alert("网络错误");
            }
        });
    });

    $("#studentCard2").change(function () {
    	layer.open({
    	    type: 2,
    	    content: '上传中。。。',
    	    shadeClose:false
    	});
    	
        var url = $("#sPic2Form").attr("action");
        var fd = new FormData(document.getElementById("sPic2Form"));
        $.ajax({
            type: "POST",
            url: $("#sPic2Form").attr("action"),
            dataType: "json",
            enctype: 'multipart/form-data',
            data: fd,
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false,
            success: function (data1) {
                s2 = true;
                $("#s2").attr("src", $.cookie("student2"));
                layer.closeAll('loading');
            },
            error: function () {
            	layer.closeAll('loading');
                alert("网络错误");
            }
        });
    });

    $("#chsipic").change(function () {
    	layer.open({
    	    type: 2,
    	    content: '上传中。。。',
    	    shadeClose:false
    	});
    	
        var url = $("#chsiForm").attr("action");
        var fd = new FormData(document.getElementById("chsiForm"));
        $.ajax({
            type: "POST",
            url: $("#chsiForm").attr("action"),
            dataType: "json",
            enctype: 'multipart/form-data',
            data: fd,
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false,
            success: function (data3) {
                s3 = true;
                $("#chsi").attr("src", $.cookie("chsipic1"));
                layer.closeAll('loading');
            },
            error: function () {
            	layer.closeAll('loading');
                alert("网络错误");
            }
        });
    });
    
    $("#bankpic").change(function () {
    	layer.open({
    	    type: 2,
    	    content: '上传中。。。',
    	    shadeClose:false
    	});
    	
        var url = $("#bankPicForm").attr("action");
        var fd = new FormData(document.getElementById("bankPicForm"));
        $.ajax({
            type: "POST",
            url: $("#bankPicForm").attr("action"),
            dataType: "json",
            enctype: 'multipart/form-data',
            data: fd,
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false,
            success: function (data3) {
                s4 = true;
                $("#bankp").attr("src", $.cookie("bankpic1"));
                layer.closeAll('loading');
            },
            error: function () {
            	layer.closeAll('loading');
                alert("网络错误");
            }
        });
    });
    
    $("#contractpic").change(function () {
    	layer.open({
    	    type: 2,
    	    content: '上传中。。。',
    	    shadeClose:false
    	});
    	
        var url = $("#contractForm").attr("action");
        var fd = new FormData(document.getElementById("contractForm"));
        $.ajax({
            type: "POST",
            url: $("#contractForm").attr("action"),
            dataType: "json",
            enctype: 'multipart/form-data',
            data: fd,
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false,
            success: function (data3) {
                s5 = true;
                $("#contractp").attr("src", $.cookie("contractpic"));
                layer.closeAll('loading');
            },
            error: function () {
            	layer.closeAll('loading');
                alert("网络错误");
            }
        });
    });
    
    jQuery("#sbutton").click(function () {
        if (p1 == true && p2 == true && s1 == true && s2 == true && s3 == true && s4 == true && s5 == true) {
        	$("#sbutton").click(function(){
        		$("#savestuForm").submit();
        	})
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