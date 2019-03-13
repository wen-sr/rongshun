$(function () {
    var orderId = tools.getUrlParam("orderId");
    getData(orderId);
});

function getData(orderId) {
    if(orderId == ''){
        return;
    }
    $("#list").html("");
    tools.request({
        url:'/rongshun/orders/detail',
        data:{ordersId : orderId},
        success:function(res){
            if(res != null){
                if(res != null && res.length > 0){
                    var tpl =
                        '{{#list}}' +
                        '<div class="weui-cell">' +
                        '<div class="weui-cell__bd">' +
                        '<p>{{skuName}} : {{priceOut}} * {{qty}} = {{my}}</p>' +
                        '</div>' +
                        '</div>' +
                        '{{/list}}';
                    var result = '<div class="list-header" style="color: #D74D49;height: 30px;line-height: 30px;padding-left: 15px;">订单明细:</div>';
                    result += tools.renderHtml(tpl, {list: res});
                    $('#list').append(result);
                }
            }
        }
    });
}