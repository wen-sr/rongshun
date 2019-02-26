$(function(){
    // $('#customer').next('.combo').find('input').focus(function (){
    //     $('#customer').combobox({
    //         url : "/rongshun/orders/getCustomer",
    //         valueField : 'customer',
    //         textField : 'customer',
    //         events:{blur:getHis}
    //     });
    // });
    $('#customer').combobox({
        url : "/rongshun/orders/getCustomer",
        valueField : 'customer',
        textField : 'customer',
        events:{blur:getHis}
    });
    $('#w-detail').window({
        onBeforeClose:function(){
            var customer = $("#customer").combobox('getValue');
            getDetail(customer);
        }
    });
    $("#hisDetail").click(function () {
        var customer = $("#customer").combobox('getValue');
        var his = $("#his").textbox('getValue');
        if(customer == '' || his == '0'){
            return;
        }
        hisDetail(customer);
    });
});
function hisDetail(customer) {
    $("#hisDiv").window("open");
    $("#hisDg").datagrid({
        url:'/rongshun/orders/hisInfo?customer=' + customer,
        height:'auto',
        fitColumns: true,
        fit: true,
        striped:true,
        border:true,
        singleSelect:true,
        showFooter: true,
        columns:[[{
            field:"skuName",
            title:"配件名称",
            width:50
        },{
            field:"priceOut",
            title:"售价",
            align:'center',
            width:30
        },{
            field:"qty",
            title:"数量",
            width:30
        },{
            field:"my",
            title:"总价",
            align:'center',
            width:30
        }]],
        onLoadSuccess:function(data){
            var rows = data.rows;
            var paid = rows[0].paid;
            var customer = rows[0].customer;
            var dd = rows[0].dd;
            var payable = rows[0].payable;
            var id = rows[0].ordersId;
            $("#old_id").val(id);
            $("#info_customer").html(customer);
            $("#info_dd").html("日期:" +dd);
            $("#info_paid").html("应付:" + paid);
            $("#info_payable").html("实付:" + payable);

            // $('#hisDg').datagrid('appendRow', {
            //     skuName: customer,
            //     supplier: '应付：' + paid ,
            //     qty: '实付' + payable,
            //     priceOut: dd
            // });
        }
    });
}
function getHis() {
    var customer = $("#customer").combobox('getValue');
    if(customer == ''){
        return;
    }
    $.ajax({
        type:'Post',
        url:'/rongshun/orders/getHis',
        data:"customer=" + customer,
        dataType:'json',
        success:function(res){
            if(res != null){
                $('#dd').datebox('setValue', formatterDate(new Date()));
                $("#his").textbox('setValue', res.data["i"]);
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
    return '<a href="#" onclick="plus(&quot;' +row.skuName +'&quot;,&quot;' +row.qtyFree+ '&quot;)"><i class="fa fa-plus-circle" style="font-size: 35px;color: #00ee00;font-weight: bolder" aria-hidden="true"></i></a>';
}
function addMinus(val, row, index) {
    return '<a href="#" onclick="minus(&quot;' +row.skuName +'&quot;,&quot;' +row.qtyFree+ '&quot;)"><i class="fa fa-minus-circle" style="font-size: 35px;color: red;font-weight: bolder" aria-hidden="true"></i></a>';

}
function formatterDate(date) {
    var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
    var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
        + (date.getMonth() + 1);
    return date.getFullYear() + '-' + month + '-' + day;
}
function plus(skuName,qtyFree) {
    $("#add_skuName").textbox('setValue', skuName);
    $("#add_qtyFree").textbox('setValue', qtyFree);
    $("#add_price_out").textbox('setValue', '');
    $("#add_qty").textbox('setValue', '');
    $("#d").window("open");
}
function minus(skuName,qtyFree) {
    $("#minus_skuName").textbox('setValue', skuName);
    $("#minus_qtyFree").textbox('setValue', qtyFree);
    $("#minus_qty").textbox('setValue', '');
    $("#d2").window("open");
}
function go() {
    var dd = $("#dd").textbox('getValue');
    var customer = $("#customer").textbox('getValue');
    var payable = $("#payable").textbox('getValue');
    var formData = {
        dd          : dd,
        customer    : customer,
        payable     :   payable
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
    if(customer == ''){
        return;
    }
    $.ajax({
        type:'Post',
        url:'/rongshun/orders/detail',
        data:"customer=" + customer + "&status=-1",
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
                        '<div class="list-content">售价:{{priceOut}},数量:{{qty}},总价:{{my}}元</div>' +
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
function renderHtml (tpl, data){
    var template = Hogan.compile(tpl);
    return template.render(data);
}
function addDetail() {
    var skuName = $("#add_skuName").textbox('getValue');
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

function payDone() {
    var id = $("#old_id").val();
    $.ajax({
        type:'Post',
        url:'/rongshun/orders/payDone',
        data:{ordersId : id},
        dataType:'json',
        success:function(res){
            if(res != null){
                // getDetail(customer);
                $.messager.alert("操作提示",res.msg,"info", function () {
                    $("#hisDiv").window("close");
                    getHis();
                });
            }
        },
        error:function(){
            $.messager.alert("提示","数据错误，联系管理员","error");
        }
    });
}