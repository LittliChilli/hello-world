var $checkbox = (function () {

    function checkbox_util() {
    }

    /**
     * 复选框选择效果
     */
    checkbox_util.prototype.select_checkbox_JS = function (checkboxName, checkboxAllId) {

        var checkboxes = $("[name='" + checkboxName + "']");
        var checkboxAll = $("[name=" + checkboxAllId + "]");
        var ischeckboxAll = true;

        for (var i = 0; i < checkboxes.length; i++) {
            var checkbox = checkboxes[i];
            if (checkbox.type == 'checkbox') {
                if (checkbox.checked) {
                    //
                }
                else {
                    ischeckboxAll = false;
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
    checkbox_util.prototype.select_checkbox_all_JS = function (checkboxName, checkboxAllId) {

        var checkboxes = $("[name='" + checkboxName + "']");
        var checkboxAll = $("[name=" + checkboxAllId + "]");

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

    return {
        init: function () {
            return new checkbox_util();
        }
    }

})();


