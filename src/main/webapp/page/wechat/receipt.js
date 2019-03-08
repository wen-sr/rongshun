$(function () {
    var name = tools.getUrlParam("skuName");
    if(name != ''){
        getDetail();
    }


    $("#picker").blur(function () {
        getDetail();
    });
});

function getSku() {
    tools.request({
        url: tools.getServerUrl("/sku/skuTips"),
        method: "POST",
        success: function (data, msg) {
            var skuNameArr = [];
            for(var i in data){
                skuNameArr.push(data[i].name);
            }
            $("#picker").picker({
                title: "请选择配件",
                inputReadOnly: false,
                cols: [
                    {
                        textAlign: 'center',
                        values: skuNameArr
                    }
                ],
                onChange: function(p, v, dv) {
                    getDetail();
                },
                onClose: function(p, v, d) {
                    getDetail();
                }
            });
        },
        error: function (error) {

        }
    })
}


function go() {
    var name = $.trim($("#picker").val());
    var qty = $.trim($("#qty").val());
    if (name === '') {
        $.toast("货物名称不能为空", "forbidden");
        return;
    }
    var reg = /^\d+(\.\d+)?$/;
    if (qty === '') {
        $.toast("数量不能为空", "forbidden");
        return;
    }
    if(!reg.test(qty)){
        $.toast("数量只能输入数字", "forbidden");
        return;
    }
    var formData = {
        skuName: name,
        qty: qty
    }
    $("#qty").val('');
    tools.request({
        url     : '/rongshun/receipt/add',
        data    : formData,
        success : function (data, msg) {
            console.log(msg);
            $.alert({
                title: '提示',
                text: msg,
                onOK: function () {
                    $("#picker").val('');
                    $("#qty").val('');
                }
            });
        },
        error   : function (error) {
            $.alert({
                title: '出问题了！',
                text: error,
                onOK: function () {
                    $("#picker").val('');
                    $("#qty").val('');
                }
            });
        }
    });
}

function add() {
    var name = $.trim($("#picker").val());
    if (name == '') {
        $.toast("货物名称不能为空", "forbidden");
        return;
    }
    window.open("inventoryPick.html?skuName=" + name, "_self");
    //
    // $("#dg").datagrid({
    //     url: '/rongshun/inventory/info',
    //     height: 'auto',
    //     fitColumns: true,
    //     fit: true,
    //     striped: true,
    //     border: true,
    //     singleSelect: true,
    //     showFooter: true,
    //     // onClickCell: onClickCell,
    //     // toolbar:'#tb',
    //     columns: [[{
    //         field: "skuName",
    //         title: "配件名称",
    //         width: 50
    //     }, {
    //         field: "qtyFree",
    //         title: "库存",
    //         width: 30
    //     }, {
    //         field: "skuId",
    //         title: "添加",
    //         formatter: addPlus,
    //         align: 'center',
    //         width: 30
    //     }, {
    //         field: "id",
    //         title: "删减",
    //         formatter: addMinus,
    //         align: 'center',
    //         width: 30
    //     }]]
    // });
}

function addPlus(val, row, index) {
    return '<a href="#" onclick="plus(&quot;' + row.skuName + '&quot;,&quot;' + row.skuId + '&quot;,&quot;' + row.qtyFree + '&quot;)"><i class="fa fa-plus-circle" style="font-size: 35px;color: #00ee00;font-weight: bolder" aria-hidden="true"></i></a>';
}

function addMinus(val, row, index) {
    return '<a href="#" onclick="minus(&quot;' + row.skuName + '&quot;,&quot;' + row.skuId + '&quot;,&quot;' + row.qtyFree + '&quot;)"><i class="fa fa-minus-circle" style="font-size: 35px;color: red;font-weight: bolder" aria-hidden="true"></i></a>';

}

function plus(skuName, skuId, qtyFree) {
    $("#add_skuName").textbox('setValue', skuName);
    $("#add_qtyFree").textbox('setValue', qtyFree);
    $("#add_qty").textbox('setValue', '');
    $("#add_skuId").val(skuId);
    $("#d").window("open");
}

function minus(skuName, skuId, qtyFree) {
    $("#minus_skuName").textbox('setValue', skuName);
    $("#minus_qtyFree").textbox('setValue', qtyFree);
    $("#minus_qty").textbox('setValue', '');
    $("#minus_skuId").val(skuId);
    $("#d2").window("open");
}

function renderHtml(tpl, data) {
    var template = Hogan.compile(tpl);
    return template.render(data);
}

function addDetail() {
    var skuId = $("#add_skuId").val();
    var qty = $("#add_qty").textbox('getValue');
    var skuName = $("#name").combobox('getValue');
    var reg = /^\d+(\.\d+)?$/;
    if (qty == '') {
        $.messager.alert("操作提示", "数量不能为空！", "error");
        return;
    }
    if (!reg.test(qty)) {
        $.messager.alert("操作提示", "数量只能输入数字！", "error");
        return;
    }
    var formData = {
        skuId: skuId,
        qty: qty,
        skuName: skuName
    }
    $.ajax({
        type: 'Post',
        url: '/rongshun/sku/build',
        data: formData,
        dataType: 'json',
        success: function (res) {
            if (res != null) {
                // getDetail(customer);
                $.messager.alert("操作提示", res.msg, "info", function () {
                    $("#d").window("close");
                    add();
                });
            }
        },
        error: function () {
            $.messager.alert("提示", "数据错误，联系管理员", "error");
        }
    });
}

function minusDetail() {
    var skuName = $("#minus_skuName").textbox('getValue');
    var qty = $("#minus_qty").textbox('getValue');
    var dd = $("#dd").textbox('getValue');
    var customer = $("#customer").textbox('getValue');
    var reg = /^\d+(\.\d+)?$/;
    if (qty == '') {
        $.messager.alert("操作提示", "数量不能为空！", "error");
        return;
    }
    if (!reg.test(qty)) {
        $.messager.alert("操作提示", "数量只能输入数字！", "error");
        return;
    }
    var formData = {
        skuName: skuName,
        qty: 0 - qty,
        dd: dd,
        customer: customer
    }
    $.ajax({
        type: 'Post',
        url: '/rongshun/orders/add',
        data: formData,
        dataType: 'json',
        success: function (res) {
            if (res != null) {
                // getDetail(customer);
                $.messager.alert("操作提示", res.msg, "info", function () {
                    $("#d2").window("close");
                    add();
                });
            }
        },
        error: function () {
            $.messager.alert("提示", "数据错误，联系管理员", "error");
        }
    });
}

function getDetail() {
    var skuName = $("#picker").val();
    if (skuName == '') {
        return;
    }
    tools.request({
        url: '/rongshun/sku/buildDetail',
        data: "skuName=" + skuName,
        success: function (data) {
            if (data != null && data.length > 0) {
                var tpl =
                    '{{#list}}' +
                    '<div class="weui-cell">' +
                    '<div class="weui-cell__bd">' +
                    '<p>{{name}} * {{qty}}</p>' +
                    '</div>' +
                    '</div>' +
                    '{{/list}}';
                var result = '<div class="list-header" style="color: #D74D49;height: 30px;line-height: 30px;padding-left: 15px;">该总成由以下零件组成:</div>';
                result += renderHtml(tpl, {list: data});
                $("#list").html('');
                $('#list').append(result);
            }
        },
        error: function () {
            $.messager.alert("提示", "数据错误，联系管理员", "error");
        }
    });
}