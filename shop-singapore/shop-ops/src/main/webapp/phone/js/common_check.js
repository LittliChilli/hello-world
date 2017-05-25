//1.号码
var reg_phone = /^1\d{10}$/;

//2.身份证
var reg_id = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;

//3.银行卡号
var reg_bank = /^(\d{16}|\d{19})$/;
;

//4.邮箱
var reg_email = /^(?:\w+\.?)*\w+@(?:\w+\.)+\w+$/;

//5.支付宝(邮箱加手机)

function validate(th, type) {
	
    //号码判断 只能是数字
    if (type == 1) {
        var phone = new RegExp(reg_phone);
        var result = phone.test(th);
        if (!result) {
//            alert("手机号码格式错误");
            return false;
        }
    }

    //判断身份证有效性
    if (type == 2) {
        var id = new RegExp(reg_id);
        var result = id.test(th);
        if (!result) {
            alert("请填写正确的身份证");
            return false;
        } else {
            var x = str.charAt(str.length - 2);
            if ((x % 2) != "0") {
                alert("只限女性注册");
                return false;
            }
        }
    }

    //判断银行卡号
    if (type == 3) {
        var bank_code = new RegExp(reg_bank);
        var result = bank_code.test(th);
        if (!result) {
            alert("请填写正确的银行卡号");
            return false;
        }
    }

    //判断支付宝账号
    if (type == 5) {
        var phone = new RegExp(reg_phone);
        var eamil = new RegExp(reg_email);
        var res_email = eamil.test(th);
        var res_phone = phone.test(th);
        if (!res_email && !res_phone) {
            alert("请填写正确的支付宝账号");
            return false;
        }
    }

    return true;
}