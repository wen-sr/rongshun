$(function () {
    var skuName = tools.getUrlParam("skuName");
    var skuId = tools.getUrlParam("skuId");
    var customer = tools.getUrlParam("customer");
    var qtyFree = tools.getUrlParam("qtyFree");
    var dd = tools.getUrlParam("dd");
    $("#customer").html(customer);
    $("#skuId").val(skuId);
    $("#qtyFree").val(qtyFree);
    $("#skuName").val(skuName);
    $("#dd").val(dd);
})

function add() {
    var skuId = $("#skuId").val();
    var qty = $("#qty").val();
    var customer = $("#customer").html();
    var skuName = $("#skuName").val();
    var priceOut = $("#priceOut").val();
    var dd = $("#dd").val();
    var reg = /^\d+(\.\d+)?$/;
    if(qty == ''){
        $.toast("数量不能为空", "forbidden");
        return;
    }
    if(!reg.test(qty)){
        $.toast("数量只能输入数字", "forbidden");
        return;
    }
    var formData = {
        skuId       : skuId,
        qty         : qty,
        skuName     : skuName,
        customer    : customer,
        dd          : dd,
        priceOut    : priceOut
    }
    tools.request({
        url:'/rongshun/orders/add',
        data:formData,
        success:function(data, msg){
            console.log(msg);
            $.alert({
                title: '提示',
                text: msg,
                onOK: function () {
                    window.open("inventoryOrdersPick.html?customer=" + customer, "_self");
                }
            });
        }
    });
}