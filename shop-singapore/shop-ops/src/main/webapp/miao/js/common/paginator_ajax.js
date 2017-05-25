/**
 * ajax分页
 * Author:邹晟
 * @param arr 写在table页面的内容
 * @param ajaxPage page实体
 * @param fnName 分页调用的方法
 */
function setAjaxPage(arr, pageInfo, fnName) {

	/*arr.push("<div class='nav_page mt10'>"
	+ "总共<span class='nav_page mt10'>" + ajaxPage.totalItem
	+ "</span>条记录&nbsp;");*/

    if (pageInfo.pageNum == 1) {
        arr.push("首页 上一页 ");
    } else {
        var pageNum = pageInfo.pageNum - 1;
        arr.push("<a id='startPage' href='javascript:void(0)' onclick='"
            + fnName + "(1)'>首页</a>"
            + "<a id='prevPage' href='javascript:void(0)' onclick='"
            + fnName + "(" + pageNum + ")'>上一页</a> ");
    }
    arr.push("当前第<span class='nav_page mt10'>" + pageInfo.pageNum
        + "</span>/<span id='totalPage'>" + pageInfo.pages
        + "</span>页");
    if (pageInfo.pageNum == pageInfo.pages || pageInfo.pages==0) {
        arr.push("&nbsp;下一页 末页");
    } else {
        var pageNum = pageInfo.pageNum + 1;
        arr.push("<a id='nextPage' href='javascript:void(0)' onclick='"
            + fnName + "(" + pageNum + ")'>下一页</a>"
            + "<a id='lastPage' href='javascript:void(0)' onclick='"
            + fnName + "(" + pageInfo.pages + ")'>末页</a> ");
    }
    arr.push("转到<input type='text' class='page_input' id='pageValue' >页"
        + "<input type='button' class='btn_go btn_ok ml5' value='GO' onclick='pageButton("
        + fnName + ")'>" + "</div>");
}

/**
 *
 * @param fnName
 */
function pageButton(fnName) {
    var pageValue = $("#pageValue").val();
    var reg = /^([1-9][0-9]*)$/;
    if (!reg.test(pageValue)) {
        alertFailureInfo("请输入正确的页号");
        return;
    }
    var totalPage = $("#totalPage").text();
    if (parseInt(pageValue) > parseInt(totalPage)) {
        alertFailureInfo("请输入不超过总页数的页号");
        return;
    }

    fnName.apply(this, [pageValue]);
}
