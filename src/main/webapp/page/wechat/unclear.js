$(function () {
    $("#begin").calendar({
        onChange: function (p, values, displayValues) {
            console.log(values, displayValues);
        }
    });
    $("#end").calendar({
        onChange: function (p, values, displayValues) {
            console.log(values, displayValues);
        }
    });
})

function go() {
    var begin = $("#begin").val();
    var end = $("#end").val();
    $('#list').html('');
    tools.request({
        url     : "/rongshun/orders/unclearInfo",
        data    : {begin : begin, end : end},
        success : function (res, msg) {
            $("#totalSize").html(res.list.length);
            $("#totalSales").html(res.totalSales);
            $("#getted").html(res.getted);
            $("#ungetted").html(res.ungetted);

            if(res["list"] != null && res["list"].length > 0){
                var tpl =
                    '{{#list}}' +
                    '<div class="weui-cell">' +
                        '<div class="weui-cell__bd">' +
                            '<div>{{customer}} : {{dd}}</div>' +
                            '<div>总额：{{paid}},已付：{{payable}},未付：{{unGetted}}</div>' +
                        '</div>' +
                    '</div>' +
                    '{{/list}}';
                var result = '<div class="list-header" style="color: #D74D49;height: 30px;line-height: 30px;padding-left: 15px;">订单明细:</div>';
                result += tools.renderHtml(tpl, {list: res["list"]});
                $('#list').append(result);
            }
        }
    })

}