$(function(){
    $('#skuName').next('.combo').find('input').focus(function (){
        $('#skuName').combobox({
            url : "/rongshun/receipt/receiptSkuTips",
            valueField : 'skuName',
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
    var name = $.trim($("#skuName").combobox('getValue'));
    var supplier = $.trim($("#supplier").combobox('getValue'));
    window.open('/rongshun/page/wechat/inventory_detail.html?skuName=' + name + '&supplier=' + supplier ,'_self');
}

