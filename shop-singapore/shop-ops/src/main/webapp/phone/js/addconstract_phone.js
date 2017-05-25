jQuery(document).ready(function () {
    var s1 = false;
    var s2 = false;

    
    
    $("#contractpic1").change(function () {
    	layer.open({
    	    type: 2,
    	    content: '上传中。。。',
    	    shadeClose:false
    	});
    	
        var url = $("#contractForm1").attr("action");
        var fd = new FormData(document.getElementById("contractForm1"));
        $.ajax({
            type: "POST",
            url: $("#contractForm1").attr("action"),
            dataType: "json",
            enctype: 'multipart/form-data',
            data: fd,
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false,
            success: function (data3) {
                s1 = true;
                $("#contractp1").attr("src", $.cookie("contractpic"+data3.data.position));
                layer.closeAll('loading');
            },
            error: function () {
            	layer.closeAll('loading');
                alert("网络错误");
            }
        });
    });
    
    $("#contractpic2").change(function () {
    	layer.open({
    	    type: 2,
    	    content: '上传中。。。',
    	    shadeClose:false
    	});
    	
        var url = $("#contractForm2").attr("action");
        var fd = new FormData(document.getElementById("contractForm2"));
        $.ajax({
            type: "POST",
            url: $("#contractForm2").attr("action"),
            dataType: "json",
            enctype: 'multipart/form-data',
            data: fd,
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false,
            success: function (data3) {
                s2 = true;
                $("#contractp2").attr("src", $.cookie("contractpic"+data3.data.position));
                layer.closeAll('loading');
            },
            error: function () {
            	layer.closeAll('loading');
                alert("网络错误");
            }
        });
    });
    
    
    jQuery("#sbutton").click(function () {
        if (s1 == true && s2 == true ) {
        	$("#savestuForm").submit();
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