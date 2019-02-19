$(function() {
    var data = UrlParm.parm("customer");
    $("#customer").val(data);
    $.ajax({
        type: 'Post',
        url: '/rongshun/inventory/info',
        data: '',
        dataType: 'json',
        success: function (res) {
            if (res.data) {
                var html = "";
                for (var i in res.data) {
                    html += "<li>"+ res.data[i].skuName +
                        "<div class='m-right'>" +
                        "<input prompt='售价' id='price_out' class=\"easyui-textbox\" style='width: 25%'>" +
                        // "<input prompt='数量' id='qty' class=\"easyui-textbox\" style='width: 25%'>\n" +
                        "</div>" +
                        "</li>";
                }
                $("#list").append(html);
            }
        },
        error: function () {
            $.messager.alert("提示", "数据错误，联系管理员", "error");
        }
    });
});






