$(function() {
    var data = UrlParm.parm("data");
    var formData = {
        skuName     : data.skuName,
        supplier    : data.supplier
    };
    $.ajax({
        type: 'Post',
        url: '/rongshun/inventory/info',
        data: formData,
        dataType: 'json',
        success: function (res) {
            if (res.data) {
                var html = "";
                for (var i in res.data) {
                    html += "<li>\n" +
                        "    <div class=\"list-header\">" + res.data[i].skuName + "</div>\n" +
                        "        <div class=\"list-content\">供应商:" + res.data[i].supplier + "已卖:" +
                        res.data[i].qtyShipped + "剩余:" + res.data[i].qtyFree + "</div>\n" +
                        "        </li>"
                }
                $("#list").append(html);
            }
        },
        error: function () {
            $.messager.alert("提示", "数据错误，联系管理员", "error");
        }
    });
});






