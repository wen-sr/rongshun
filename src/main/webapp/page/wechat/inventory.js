$(function(){
    $('#skuName').next('.combo').find('input').focus(function (){
        $('#skuName').combobox({
            url : "/rongshun/receipt/receiptSkuTips",
            valueField : 'skuId',
            textField : 'skuName'
        });

    });
    $('#supplier').next('.combo').find('input').focus(function (){
        $('#supplier').combobox({
            url : "/rongshun/receipt/receiptSupplierTips",
            valueField : 'supplier',
            textField : 'supplier'
        });

    });
});

function go() {
    var name = $.trim($("#skuName").combobox('getText'));
    var supplier = $.trim($("#supplier").combobox('getValue'));
    var formData = {
        skuName     : name,
        supplier    : supplier
    };
    window.open('/rongshun/page/wechat/inventory_detail.html?data=' + formData ,'_self');
}

