$(function(){
    $('#name').next('.combo').find('input').focus(function (){
        $('#name').combobox({
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

function add() {
    $("#dg").datagrid({
        url:'/rongshun/inventory/info',
        height:'auto',
        fitColumns: true,
        fit: true,
        striped:true,
        border:true,
        singleSelect:true,
        showFooter: true,
        onClickCell: onClickCell,
        // toolbar:'#tb',
        columns:[[{
            field:"skuName",
            title:"配件名称",
            width:50
        },{
            field:"supplier",
            title:"配件供商",
            width:50
        },{
            field:"qtyFree",
            title:"库存",
            width:30
        },{
            field:"skuId",
            title:"添加",
            formatter:addPlus,
            align:'center',
            width:30
        },{
            field:"id",
            title:"删减",
            formatter:addMinus,
            align:'center',
            width:30
        }]]
    });

    $("#w-detail").window("open");
}

function addPlus(val, row, index) {
    return '<a href="#" onclick="plus()"><i class="fa fa-plus-circle" style="font-size: 35px;color: #00ee00;font-weight: bolder" aria-hidden="true"></i></a>';

}
function addMinus(val, row, index) {
    return '<a href="#" onclick="minus()"><i class="fa fa-minus-circle" style="font-size: 35px;color: red;font-weight: bolder" aria-hidden="true"></i></a>';

}
function plus() {
    var row = $("#dg").datagrid('getSelected');
    $("#add_skuName").textbox('setValue', row.skuName);
    $("#add_supplier").textbox('setValue', row.supplier);
    $("#add_qtyFree").textbox('setValue', row.qtyFree);
    $("#d").window("open");
}
function minus() {
    var row = $("#dg").datagrid('getSelected');
    $("#add_skuName").textbox('setValue', row.skuName);
    $("#add_supplier").textbox('setValue', row.supplier);
    $("#add_qtyFree").textbox('setValue', row.qtyFree);
    $("#d").window("open");
}
function onClickCell(index, field){
    if (endEditing()){
        $('#dg').datagrid('selectRow', index)
            .datagrid('editCell', {index:index,field:field});
        editIndex = index;
    }
}
$.extend($.fn.datagrid.methods, {
    editCell: function(jq,param){
        return jq.each(function(){
            var opts = $(this).datagrid('options');
            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field){
                    col.editor = null;
                }
            }
            $(this).datagrid('beginEdit', param.index);
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});
function renderHtml (tpl, data){
    var template = Hogan.compile(tpl);
    var result = template.render(data);
    return result;
};

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

