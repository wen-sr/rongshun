$(function () {
    var skuName = tools.getUrlParam("skuName");
    $("#list").html('');
    tools.request({
        url: "/rongshun/inventory/info",
        data: {skuName : skuName},
        success: function (data, msg) {
            if (data != null && data.length > 0) {
                var tpl =
                    '{{#list}}' +
                    '<div class="weui-cell">' +
                        '<div class="weui-cell__bd">' +
                            '<p>{{skuName}}</p>' +
                            '<p>总收货：{{qtyReceipt}}，总发货:{{qtyShipped}}，可用库存:{{qtyFree}}</p>' +
                        '</div>' +
                    '</div>'+
                '{{/list}}';
                var result = tools.renderHtml(tpl, {list: data});
                $("#list").append(result);
            }
        }
    });
});
