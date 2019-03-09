$(function () {
    var customer = tools.getUrlParam("customer");
    var dd = tools.getUrlParam("dd");
    $("#customer").val(customer);
    $("#dd").val(dd);
    $("#list").html('');
    tools.request({
        url: "/rongshun/inventory/info",
        success: function (data, msg) {
            if (data != null && data.length > 0) {
                var tpl =
                    '{{#list}}' +
                    '<div class="table-view">' +
                        '<div class="table-colum1">{{skuName}}</div>' +
                        '<div class="table-colum2">{{qtyFree}}</div>' +
                        '<div class="table-colum3">' +
                                '<a href="addOrdersDetail.html?skuName={{skuName}}&qtyFree={{qtyFree}}&&customer='+ customer + '&skuId={{skuId}}&dd=' + dd +'">' +
                                '<i class="fa fa-plus-circle"></i>' +
                                '</a>' +
                                '<i class="fa fa-ellipsis-v" aria-hidden="true" style="color: #666;"></i>'+
                            '<a href="minusOrdersDetail.html?skuName={{skuName}}&qtyFree={{qtyFree}}&&customer='+ customer + '&skuId={{skuId}}&dd=' + dd +'">' +
                            '<i class="fa fa-minus-circle" style="color:red"></i>' +
                            '</a>' +
                        '</div>' +
                    '</div>'+
                '{{/list}}';
                var result = tools.renderHtml(tpl, {list: data});
                $("#list").append(result);
            }
        }
    });

});

function back() {
    var customer = $("#customer").val();
    var dd = $("#dd").val();
    window.open('orders.html?customer='+ customer + '&dd=' + dd,'_self');
}
