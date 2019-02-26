$(function(){
    // $('#skuName').next('.combo').find('input').focus(function (){
    //     $('#skuName').combobox({
    //         url : "/rongshun/receipt/receiptSkuTips",
    //         valueField : 'skuName',
    //         textField : 'skuName'
    //     });
    //
    // });
    $('#skuName').combobox({
        url : "/rongshun/receipt/receiptSkuTips",
        valueField : 'skuName',
        textField : 'skuName'
    });

});

function go() {
    var name = $.trim($("#skuName").combobox('getValue'));
    window.open('/rongshun/page/wechat/inventory_detail.html?skuName=' + name ,'_self');
}

