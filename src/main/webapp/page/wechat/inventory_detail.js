$(function() {
    var skuName = UrlParm.parm("skuName");
    var formData = {
        skuName     : skuName
    };
    $.ajax({
        type: 'Post',
        url: '/rongshun/inventory/infoTips',
        data: formData,
        dataType: 'json',
        success: function (res) {
            if (res.data) {
                var html = "";
                for (var i in res.data) {
                    html += "<li>\n" +
                        "    <div class=\"list-header\">" + res.data[i].skuName + "</div>\n" +
                        "        <div class=\"list-content\">总收货:"+
                        res.data[i].qtyReceipt+"，已卖:" +
                        res.data[i].qtyShipped + "，剩余库存:" + res.data[i].qtyFree + "</div>\n" +
                        "        </li>"
                }
                var screen = $(window).height();
                $("#list").height(parseInt(screen) - 10);
                $("#list").append(html);
            }
        },
        error: function () {
            $.messager.alert("提示", "数据错误，联系管理员", "error");
        }
    });
});






