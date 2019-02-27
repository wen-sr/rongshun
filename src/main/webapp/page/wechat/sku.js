$(function(){
    $('#name').combobox({
        url : "/rongshun/sku/skuTips",
        valueField : 'name',
        textField : 'name'
    });

    $('#w-detail').window({
        onBeforeClose:function(){
            var skuName = $("#name").combobox('getValue');
            getDetail(skuName);
        }
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

function add() {
    var name = $.trim($("#name").combobox('getText'));
    if(name == ''){
        $.messager.alert("操作提示","请先输入总成配件的名称！","error");
        return;
    }
    $("#w-detail").window("open");
    $("#dg").datagrid({
        url:'/rongshun/inventory/info',
        height:'auto',
        fitColumns: true,
        fit: true,
        striped:true,
        border:true,
        singleSelect:true,
        showFooter: true,
        // onClickCell: onClickCell,
        // toolbar:'#tb',
        columns:[[{
            field:"skuName",
            title:"配件名称",
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
}

function addPlus(val, row, index) {
    return '<a href="#" onclick="plus(&quot;' +row.skuName +'&quot;,&quot;' +row.skuId+ '&quot;,&quot;' +row.qtyFree+ '&quot;)"><i class="fa fa-plus-circle" style="font-size: 35px;color: #00ee00;font-weight: bolder" aria-hidden="true"></i></a>';
}
function addMinus(val, row, index) {
    return '<a href="#" onclick="minus(&quot;' +row.skuName +'&quot;,&quot;' +row.skuId+ '&quot;,&quot;' +row.qtyFree+ '&quot;)"><i class="fa fa-minus-circle" style="font-size: 35px;color: red;font-weight: bolder" aria-hidden="true"></i></a>';

}
function plus(skuName,skuId,qtyFree) {
    $("#add_skuName").textbox('setValue', skuName);
    $("#add_qtyFree").textbox('setValue', qtyFree);
    $("#add_qty").textbox('setValue', '');
    $("#add_skuId").val(skuId);
    $("#d").window("open");
}
function minus(skuName,skuId,qtyFree) {
    $("#minus_skuName").textbox('setValue', skuName);
    $("#minus_qtyFree").textbox('setValue', qtyFree);
    $("#minus_qty").textbox('setValue', '');
    $("#minus_skuId").val(skuId);
    $("#d2").window("open");
}
function renderHtml (tpl, data){
    var template = Hogan.compile(tpl);
    return template.render(data);
}
function addDetail() {
    var skuId = $("#add_skuId").val();
    var qty = $("#add_qty").textbox('getValue');
    var skuName = $("#name").combobox('getValue');
    var reg = /^\d+(\.\d+)?$/;
    if(qty == ''){
        $.messager.alert("操作提示","数量不能为空！","error");
        return;
    }
    if(!reg.test(qty)){
        $.messager.alert("操作提示","数量只能输入数字！","error");
        return;
    }
    var formData = {
        skuId       : skuId,
        qty         : qty,
        skuName     : skuName
    }
    $.ajax({
        type:'Post',
        url:'/rongshun/sku/build',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res != null){
                // getDetail(customer);
                $.messager.alert("操作提示",res.msg,"info", function () {
                    $("#d").window("close");
                    add();
                });
            }
        },
        error:function(){
            $.messager.alert("提示","数据错误，联系管理员","error");
        }
    });
}
function minusDetail() {
    var skuName = $("#minus_skuName").textbox('getValue');
    var qty = $("#minus_qty").textbox('getValue');
    var dd = $("#dd").textbox('getValue');
    var customer = $("#customer").textbox('getValue');
    var reg = /^\d+(\.\d+)?$/;
    if(qty == ''){
        $.messager.alert("操作提示","数量不能为空！","error");
        return;
    }
    if(!reg.test(qty)){
        $.messager.alert("操作提示","数量只能输入数字！","error");
        return;
    }
    var formData = {
        skuName     : skuName,
        qty         : 0-qty,
        dd          : dd,
        customer    : customer
    }
    $.ajax({
        type:'Post',
        url:'/rongshun/orders/add',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res != null){
                // getDetail(customer);
                $.messager.alert("操作提示",res.msg,"info", function () {
                    $("#d2").window("close");
                    add();
                });
            }
        },
        error:function(){
            $.messager.alert("提示","数据错误，联系管理员","error");
        }
    });
}

function getDetail(skuName){
    if(skuName == ''){
        return;
    }
    $.ajax({
        type:'Post',
        url:'/rongshun/sku/buildDetail',
        data:"skuName=" + skuName,
        dataType:'json',
        success:function(res){
            if(res.data != null){
                var tpl =
                    '{{#list}}'+
                    '<li style="line-height: 25px;">' +
                    '<div class="list-content">{{name}},数量:{{qty}}</div>' +
                    '</li>' +
                    '{{/list}}';
                var result = '<li line-height: 25px;><div class="list-header" style="color: #D74D49;">订单明细</div></li>';
                result += renderHtml(tpl, {list:res.data});
                if(res.data.length >= 4){
                    var screen = $(window).height();
                    $("#list").height(parseInt(screen) - 270);
                }else {
                    $("#list").height('');
                }
                $("#list").html('');
                $('#list').append(result);
            }
        },
        error:function(){
            $.messager.alert("提示","数据错误，联系管理员","error");
        }
    });
}