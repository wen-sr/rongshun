$(function () {
    var customer = tools.getUrlParam("customer");
    $("#customer").html(customer);
    $("#list").html('');
    tools.request({
        url: "/rongshun/orders/hisInfo",
        data: {customer : customer},
        success: function (data, msg) {
            $("#dd").html(data.dd);
            $("#paid").html(data.paid);
            $("#payable").html(data.payable);
            $("#other").html(data.paid - data.payable);
            if (data.list != null && data.list.length > 0) {
                var tpl =
                    '{{#list}}' +
                    '<div class="weui-cell">' +
                        '<div class="weui-cell__bd">' +
                            '<p>{{skuName}}：{{priceOut}} * {{qty}} = {{my}}</p>' +
                        '</div>' +
                    '</div>'+
                '{{/list}}';
                var result = tools.renderHtml(tpl, {list: data.list});
                $("#list").append(result);
            }
        }
    });
});

function back() {
    var customer = $("#customer").html();
    window.open('orders.html?customer='+ customer,'_self');
}

function go() {
    var that = this;
    var add_payable = $("#add_payable").val();
    if (add_payable === '') {
        $.toast("请先输入补付金额", "forbidden");
        return;
    }
    var customer = $("#customer").html();
    tools.request({
        url     : "/rongshun/orders/addPayble",
        data    : {customer : customer, payable : add_payable},
        success : function (data, msg) {
            $.alert({
                title: '提示！',
                text: msg,
                onOK: function () {
                    that.back();
                }
            });
        }
    })
}