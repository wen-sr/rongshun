$(function () {
    var skuName = tools.getUrlParam("skuName");
    var skuId = tools.getUrlParam("skuId");
    var newName = tools.getUrlParam("newName");
    var qtyFree = tools.getUrlParam("qtyFree");
    $("#name").html(newName);
    $("#skuId").val(skuId);
    $("#qtyFree").val(qtyFree);
    $("#skuName").val(skuName);
})

function addDetail() {
    var skuId = $("#skuId").val();
    var qty = $("#qty").val();
    var skuName = $("#name").html();
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
        skuName     : skuName
    }
    tools.request({
        url:'/rongshun/sku/build',
        data:formData,
        success:function(data, msg){
            $.alert({
                title: '提示',
                text: msg,
                onOK: function () {
                    window.open("inventoryPick.html?newName=" + skuName, "_self");
                }
            });
        }
    });
}