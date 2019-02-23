$(function(){
    $('#customer').next('.combo').find('input').focus(function (){
        $('#customer').combobox({
            url : "/rongshun/orders/getCustomers",
            valueField : 'skuName',
            textField : 'skuName',
            events:{blur:getHis}
        });
    });
});

function getHis() {
    var customer = $("#customer").combobox('getValue');
    $.ajax({
        type:'Post',
        url:'/rongshun/orders/getHis',
        data:"customer=" + customer,
        dataType:'json',
        success:function(res){
            if(res != null){
                $('#dd').datebox('setValue', formatterDate(new Date()));
                $("#his").textbox('setValue', res.data);
                getDetail(customer);
            }
        },
        error:function(){
            $.messager.alert("提示","数据错误，联系管理员","error");
        }
    });
}
function add() {
    var customer = $("#customer").combobox('getValue');
    if(customer == ''){
        $.messager.alert("操作提示","请先输入客户名称！","error");
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
}

function addPlus(val, row, index) {
    return '<a href="#" onclick="plus(&quot;' +row.skuName +'&quot;,&quot;'+ row.supplier +'&quot;,&quot;' +row.qtyFree+ '&quot;)"><i class="fa fa-plus-circle" style="font-size: 35px;color: #00ee00;font-weight: bolder" aria-hidden="true"></i></a>';
}
function addMinus(val, row, index) {
    return '<a href="#" onclick="minus(&quot;' +row.skuName +'&quot;,&quot;'+ row.supplier +'&quot;,&quot;' +row.qtyFree+ '&quot;)"><i class="fa fa-minus-circle" style="font-size: 35px;color: red;font-weight: bolder" aria-hidden="true"></i></a>';

}
function formatterDate(date) {
    var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
    var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
        + (date.getMonth() + 1);
    return date.getFullYear() + '-' + month + '-' + day;
}
function plus(skuName,supplier,qtyFree) {
    $("#add_skuName").textbox('setValue', skuName);
    $("#add_supplier").textbox('setValue', supplier);
    $("#add_qtyFree").textbox('setValue', qtyFree);
    $("#add_price_out").textbox('setValue', '');
    $("#add_qty").textbox('setValue', '');
    $("#d").window("open");
}
function minus(skuName,supplier,qtyFree) {
    $("#add_skuName").textbox('setValue', skuName);
    $("#add_supplier").textbox('setValue', supplier);
    $("#add_qtyFree").textbox('setValue', qtyFree);
    $("#add_price_out").textbox('setValue', '');
    $("#add_qty").textbox('setValue', '');
    $("#d").window("open");
}
function go() {
    var dd = $("#dd").textbox('getValue');
    var customer = $("#customer").textbox('getValue');
    var payable = $("#payable").textbox('getValue');
    var formData = {
        dd          : dd,
        customer    : customer,
        payable      :   payable
    };
    $("#go").linkbutton('disable');
    $.ajax({
        type:'Post',
        url:'/rongshun/orders/confirm',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res != null){
                $.messager.alert("操作提示",res.msg,"info", function () {
                    $('#ff').form('reset');
                    $("#list").html('');
                    $("#go").linkbutton('enable');
                });
            }
        },
        error:function(){
            $.messager.alert("提示","数据错误，联系管理员","error");
        }
    });
}

function getDetail(customer){
    $.ajax({
        type:'Post',
        url:'/rongshun/orders/detail',
        data:"customer=" + customer,
        dataType:'json',
        success:function(res){
            if(res.data != null){
                var my = 0;
                for(var i in res.data){
                    my += res.data[i].my;
                }
                $("#my").textbox('setValue', my);
                var tpl =
                    '{{#list}}'+
                    '<li style="line-height: 25px;">' +
                        '<div class="list-header">{{skuName}}</div>' +
                        '<div class="list-content">{{supplier}}:{{priceOut}}*{{qty}}={{my}}元</div>' +
                    '</li>' +
                    '{{/list}}';
                var result = renderHtml(tpl, {list:res.data});
                $("#list").html('');
                $('#list').append(result);
            }
        },
        error:function(){
            $.messager.alert("提示","数据错误，联系管理员","error");
        }
    });
}
function renderHtml (tpl, data){
    var template = Hogan.compile(tpl);
    return template.render(data);
};
function addDetail() {
    var skuName = $("#add_skuName").textbox('getValue');
    var supplier = $("#add_supplier").textbox('getValue');
    var price_out = $("#add_price_out").textbox('getValue');
    var qty = $("#add_qty").textbox('getValue');
    var dd = $("#dd").textbox('getValue');
    var customer = $("#customer").textbox('getValue');
    var reg = /^\d+(\.\d+)?$/;
    if(price_out == ''){
        $.messager.alert("操作提示","售价不能为空！","error");
        return;
    }
    if(qty == ''){
        $.messager.alert("操作提示","数量不能为空！","error");
        return;
    }
    if(!reg.test(price_out)){
        $.messager.alert("操作提示","售价只能输入数字！","error");
        return;
    }
    if(!reg.test(qty)){
        $.messager.alert("操作提示","数量只能输入数字！","error");
        return;
    }
    var formData = {
        skuName     : skuName,
        supplier    : supplier,
        priceOut    : price_out,
        qty         : qty,
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
                getDetail(customer);
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