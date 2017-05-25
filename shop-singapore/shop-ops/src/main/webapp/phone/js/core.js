$(function () {
    if (typeof MD === "undefined") {
        MD = {};
    }
    ;
    MD.dialog = function (opts) {
        var defaults = {
            id: 'dialogWrap',
            appendTo: 'body',
            mask: true, //遮罩层
            opacity: 0.2, //遮罩层背景透明度
            overlayBg: "#000",
            overlayZindex: 9999,
            closeDom: '',
            html: '', //中心区HTML内容
            initFn: null,
            callBack: null
        };
        var opts = $.extend(defaults, opts);
        this.id = opts.id;
        this.appendTo = opts.appendTo;
        this.mask = opts.mask;
        this.opacity = opts.opacity;
        this.overlayBg = opts.overlayBg;
        this.overlayZindex = opts.overlayZindex;
        this.closeDom = opts.closeDom;
        this.html = opts.html;
        this.callBack = opts.callBack;
        this.initFn = opts.initFn;
        //this.callBack = opts.callBack;  this.callBack为新生成的对象添加一个callBack函数，此时this.callBack的指针指向opts对象里面的opts.callBack函数,以下的检测代码
        //console.info(this.callBack === opts.callBack); //true
        this.init();
    };
    MD.dialog.prototype = {
        init: function () {
            this.bulidDom();
        },
        //初始化DOM
        bulidDom: function () {
            var _this = this;
            $('.dialog-mask,.dialog-wrap').remove();
            //创建遮罩层
            if (this.mask) {
                var $dialogMask = $('<div class="dialog-mask" id="dialogMask"></div>');
                $dialogMask.css({
                    'height': '100%',
                    'width': '100%',
                    'background': this.overlayBg,
                    'opacity': this.opacity,
                    'z-index': this.overlayZindex
                });
                // .on('tap',function(e){
                // 	e.stopPropagation();
                // 	_this.close();
                // });
                //console.info($dialogMask);
                $(this.appendTo).append($dialogMask);
                this.mask = $dialogMask;
                this.mask.on('tap touchstart touchmove', function () {
                    _this.close();
                })
            }
            ;
            var $dialogWrap = $('<div class="dialog-wrap" id="dialogWrap">' +
                    '<div class="dialog-content"></div>' +
                    '</div>'),
                $dialogContent = $dialogWrap.children('.dialog-content');
            $dialogContent.html(this.html);
            $(this.appendTo).append($dialogWrap);
            $dialogWrap.css({
                'height': $dialogContent.height(),
                'z-index': this.overlayZindex + 1000,
                'margin-top': -$dialogContent.height() / 2
            });
            this.content = $dialogWrap;
            //console.info($dialogContent.height());
        },
        show: function () {
            if (this.initFn)
                this.initFn.apply(this);
            if (this.mask)
                this.mask.show();
            this.content.css({
                'visibility': 'visible',
                'display': 'block'
            });
            if (this.callBack) {
                this.callBack.apply(this);
            }
            ;
        },
        close: function () {
            var _this = this;
            this.content.remove();
            _this.mask.css({
                'opacity': '0',
                'filter': 'Alpha(Opacity=0)'
            });
            //console.info(_this.mask,this.content)
            setTimeout(function () {
                //alert(123)
                if (_this.mask) {
                    _this.mask.remove();
                }
                ;
            }, 400);

        }
    };
    MD.evalData = function (a) {
        if (typeof(a) == "object")
            return a;
        else {
            var list = null;
            try {
                list = $.parseJSON(a);
            } catch (e) {
                eval("list=" + a);
            }
            return list;
        }
    };


    MD.createSelect = function (opts) {
        var html = [];
        if (opts.initFn)
            html = opts.initFn(html);
        for (var i in opts.data) {
            var row = opts.data[i];
            if (typeof opts.format == "function")
                html.push('<option value="' + row[opts.valField] + '">' + opts.format(row) + '</option>');
            else {
                html.push('<option value="' + row[opts.valField] + '">' + row[opts.format] + '</option>');
            }
        }
        opts.$obj.html(html.join("\n"));
        if (opts.doneFn) opts.doneFn(opts.$obj);
    };
    MD.getAjaxInfo = function (opts) {
        var defaults = {
            async: true,
            method: 'get',
            processData: true,
            erro: null,
            isLoading: true,
            verify: false,
            verifyOne: null,
            formObj: null,
            beforeSend: null,
            tip: true,
            contentType: 'application/x-www-form-urlencoded; charset=utf-8',
            formData: ''
        };
        var isSubmit = true;
        var reSerialize = false;
        var opts = $.extend(defaults, opts);
        if (opts.verify) {
            var formObj;
            if (opts.verifyOne)
                formObj = opts.verifyOne;
            else {
                reSerialize = true;
                formObj = opts.formObj.find("input[data-minlength],input[data-pattern],input[data-required],select[data-required]");
            }
            formObj.each(function (i, e) {
                var tmp = $(e).val();
                var minlength = $(e).data("minlength");
                var title = $(e).data("title");
                var requiredMsg = $(e).data("requiredmsg") || $(e).attr("placeholder");
                var strReg = $(e).data("pattern");
                var required = $(e).data("required");
                if (required) {
                    if ($(e).val() == "") {
                        isSubmit = false;
                        MD.tip(requiredMsg);
                        return false;
                    }
                }
                if (minlength)
                    if (tmp.length < minlength) {
                        isSubmit = false;
                        MD.tip(title + "至少填写" + minlength + "位");
                        return false;
                    }

                if (strReg && required) {
                    var reg = new RegExp(strReg);
                    if (!reg.test(tmp)) {
                        isSubmit = false;
                        MD.tip(title);
                        return false;
                    }
                }

            });
        }
        if (isSubmit) {
            if (opts.beforeSend) {
                opts.beforeSend();
                if (opts.verify && reSerialize)
                    opts.formData = opts.formObj.serialize();
            }
            $.ajax({
                accepts: "text/html",
                processData: opts.processData,
                contentType: opts.contentType,
                async: opts.async,
                cache: false,
                type: opts.method,
                beforeSend: function () {
                    if (opts.isLoading)
                        MD.tip("正在加载中…");
                },
                url: opts.url,
                data: opts.formData,
                dataType: 'text',
                timeout: 60000,
                success: function (msg, textStatus, R) {
                    if (opts.isLoading)
                        $("div.dialog-mask,div.dialog-wrap").remove();
                    msg = MD.evalData(msg);
                    if (msg.result == "0" || msg.result == "2")
                        opts.fn(msg);
                    else {
                        if (opts.tip)
                            MD.tip(msg.msg);
                        if (opts.erro)
                            opts.erro(msg.msg);
                    }
                },
                complete: function (textStatus) {
                    if (textStatus.statusText == "timeout") {

                    }
                }
            });
        }
    };
    MD.getLocation = function (fn, erroFn) {
        if (this.testGeolocation()) {
            navigator.geolocation.getCurrentPosition(function (h) {
                var j = h.coords.latitude;
                var i = h.coords.longitude;
                MD.getAjaxInfo({
                    url: "/m/main/seekCity.json?location=" + j + "," + i,
                    fn: function (res) {
                        $.cookie("cityId", res.data.cityId, {
                            path: "/"
                        });
                        $.cookie("cityName", res.data.cityName, {
                            path: "/"
                        });
                        if (fn)
                            fn(res);
                    },
                    tip: false,
                    isLoading: false,
                    erro: function (msg) {
                        //alert("ajaxErro");
                        if (erroFn) {
                            var error = {};
                            error.code = 0;
                            error.PERMISSION_DENIED = 1;
                            erroFn(error, msg);
                        }
                    }
                });
            }, function (error) {
                var str = "";
                switch (error.code) {
                    case error.TIMEOUT:
                        str = "连接超时";
                        break;
                    case error.PERMISSION_DENIED:
                        str = "您拒绝了使用位置共享服务，查询已取消";
                        break;
                    case error.POSITION_UNAVAILABLE:
                        str = "获取位置信息失败";
                        break;
                    default:
                        str = "Gps定位失败";
                }
                if (erroFn)
                    erroFn(error, str);
                //error.PERMISSION_DENIED

            }, {
                timeout: 5000
            });
        } else {
            MD.confirm({
                msg: "定位失败，请手动选择",
                yFn: function () {
                    window.location.href = "/web/m/wap/area/area.jsp";
                },
                nFn: function () {
                }
            });
            //alert("对不起，您的手机不支持定位功能！");
        }
    };
    MD.getLocation.prototype = {
        testGeolocation: function () {
            if (!!navigator.geolocation) {
                return true;
            }
            return false;
        }
    };
    MD.confirm = function (opts) {
        var defaults = {
            title: "提示",
            yText: "确认",
            nText: "取消",
            msg: "",
            yFn: null,
            nFn: null
        };
        var opts = $.extend(defaults, opts);
        var title = "";
        if (opts.title)
            title = '<h3>' + opts.title + '</h3>';

        var html = '<div class="confirm">' +
            title +
            '<span>' + opts.msg + '</span>' +
            '<div class="btns">' +
            '<a href="javascript:void(0)" data-type="no">' + opts.nText + '</a>' +
            '<a href="javascript:void(0)" data-type="yes">' + opts.yText + '</a>' +
            '</div>' +
            '</div>';
        new MD.dialog({
            html: html,
            callBack: function () {
                //里面的this指向新生成的$dialog对象
                var _this = this;
                this.content.find('.confirm .btns > a').on('tap', function (e) {
                    var isClose = true;
                    if ($(this).data('type') == "yes") {
                        if (opts.yFn)
                            isClose = opts.yFn(_this);
                    } else {
                        if (opts.nFn)
                            isClose = opts.nFn(_this);
                    }
                    ;
                    if (isClose || typeof isClose == "undefined") {
                        _this.close();
                        e.preventDefault();
                    }
                });
            }
        }).show();
    };
    MD.tip = function (msg, time, fn) {
        time = time || 3000;
        var html = '<div class="dialog-tip">' + msg + '</div>';
        new MD.dialog({
            html: html,
            mask: true,
            //			initFn: function() {
            //				$(this.content[0]).css({
            //					"top": "50%",
            //					"bottom": "120px"
            //				});
            //			},
            callBack: function () {
                //里面的this指向新生成的$dialog对象
                var _this = this;
                if (time > 0) {
                    var val = time;
                    (function () {
                        val -= 1000;
                        if (val > 0) {
                            setTimeout(arguments.callee, 1000);
                        } else if (val <= 0) {
                            //元素透明度为0后隐藏元素
                            _this.close();
                            if (fn) fn();
                        }
                    })();
                } else {
                    if (fn) fn(_this);
                }
            }
        }).show();
    };

    //footer
    $('#goTop,a.gotop').on('tap', function () {
        setTimeout(function () {
            window.scrollTo(0, 0);
        }, 60);
    });
    // var $fas = $('#footer > a');
    // $fas.on('tap', function (){
    //     $(this).addClass('on');
    // });


});