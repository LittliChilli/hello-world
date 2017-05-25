$(document).ready(function () {
    $("#submit").click(function () {
        $("#login_form").validate({
            rules: {
                username: {
                    required: true,
                    minlength: 6,
                    maxlength: 12
                },
                password: {
                    required: true,
                    minlength: 6,
                    maxlength: 12
                }
            },
            messages: {
                username: {
                    required: "请填写用户名",
                    minlength: "用户名最短为6位",
                    maxlength: "用户名最长为12位"
                },
                password: {
                    required: "请填写密码",
                    minlength: "密码最短为6位",
                    maxlength: "密码最长为12位"
                }
            },
            submitHandler: function (form) {
                $.ajax({
                    type: "POST",
                    url: "/admin/login",
                    dataType: "json",
                    data: $(form).serialize(),
                    success: function (data) {
                        if (data.result == "0")
                            window.location.href = "/order/toUserOrderList";
                        else
                        	alert(data.msg);
                    },
                    error: function () {
                    	alert("网络错误");
                    }
                });
            },
            //errorClass:"",

        });
    })
})