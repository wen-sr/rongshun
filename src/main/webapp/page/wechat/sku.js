$(function(){
    $('#name').next('.combo').find('input').focus(function (){
        $('#name').combobox({
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
    var name = $.trim($("#name").combobox('getText'));
    var price = $.trim($("#price").textbox('getValue'));
    var qty = $.trim($("#qty").textbox('getValue'));
    var supplier = $.trim($("#supplier").combobox('getValue'));
    if(name == ''){
        $.messager.alert("操作提示","货物名称不能为空！","error");
        return;
    }
    if(price == ''){
        $.messager.alert("操作提示","货物进价不能为空！","error");
        return;
    }
    var reg = /^\d+(\.\d+)?$/;
    if(!reg.test(price)){
        $.messager.alert("操作提示","货物进价只能输入数字！","error");
        return;
    }
    if(qty == ''){
        $.messager.alert("操作提示","数量不能为空！","error");
        return;
    }
    if(!reg.test(price)){
        $.messager.alert("操作提示","数量只能输入数字！","error");
        return;
    }
    if(supplier == ''){
        $.messager.alert("操作提示","供用商不能为空！","error");
        return;
    }
    var formData = {
        skuName     : name,
        price       : price,
        qty         :   qty,
        supplier    : supplier
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

