$(function () {
    var newName = tools.getUrlParam("newName");
    $("#newName").val(newName);
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
                                '<a href="addSkuDetail.html?skuName={{skuName}}&qtyFree={{qtyFree}}&&newName='+ newName + '&&skuId={{skuId}}">' +
                                '<i class="fa fa-plus-circle"></i>' +
                                '</a>' +
                                '<i class="fa fa-ellipsis-v" aria-hidden="true" style="color: #666;"></i>'+
                            '<a href="minusSkuDetail.html?skuName={{skuName}}&qtyFree={{qtyFree}}&&newName='+ newName + '&&skuId={{skuId}}">' +
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
    var newName = $("#newName").val();
    window.open('receipt.html?skuName='+ newName,'_self');
}
