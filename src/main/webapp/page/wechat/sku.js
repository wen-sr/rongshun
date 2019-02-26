$(function(){
    $('#name').combobox({
        url : "/rongshun/receipt/receiptSkuTips",
        valueField : 'skuName',
        textField : 'skuName'
    });

    // $('#name').next('.combo').find('input').focus(function (){
    //     $('#name').combobox({
    //         url : "/rongshun/receipt/receiptSkuTips",
    //         valueField : 'skuName',
    //         textField : 'skuName'
    //     });
    //
    // });
    // $('#supplier').next('.combo').find('input').focus(function (){
    //     $('#supplier').combobox({
    //         url : "/rongshun/receipt/receiptSupplierTips",
    //         valueField : 'supplier',
    //         textField : 'supplier'
    //     });
    //
    // });
});

function go() {
    var name = $.trim($("#name").combobox('getText'));
    var qty = $.trim($("#qty").textbox('getValue'));
    if(name == ''){
        $.messager.alert("操作提示","货物名称不能为空！","error");
        return;
    }
    var reg = /^\d+(\.\d+)?$/;
    if(qty == ''){
        $.messager.alert("操作提示","数量不能为空！","error");
        return;
    }
    var formData = {
        skuName     : name,
        qty         :   qty
    }
    $("#go").linkbutton('disable');
    $.ajax({
        type:'Post',
        url:'/rongshun/receipt/add',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res != null){
                $.messager.alert("操作提示",res.msg,"info", function () {
                    $('#ff').form('reset');
                    $("#go").linkbutton('enable');
                });
            }
        },
        error:function(){
            $.messager.alert("提示","数据错误，联系管理员","error");
        }
    });
}

