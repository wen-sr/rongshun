$(function () {
    $("#dd").calendar({
        onChange: function (p, values, displayValues) {
            console.log(values, displayValues);
        }
    });

    $("#customer").blur(function () {
       getHis();
    });

    var customer = tools.getUrlParam("customer");
    var dd = tools.getUrlParam("dd");
    if(customer != ''){
        $("#customer").val(customer);
        getHis();
    }
    if(dd != ''){
        $("#dd").val(dd);
        // $("#dd").calendar("setValue", ["2019/03/10"]);
    }
});
function getCustomer() {
    tools.request({
        url: tools.getServerUrl("/orders/getCustomer"),
        method: "POST",
        success: function (data, msg) {
            var skuNameArr = [];
            for(var i in data){
                skuNameArr.push(data[i].customer);
            }
            $("#customer").picker({
                title: "请选择客户",
                inputReadOnly: false,
                cols: [
                    {
                        textAlign: 'center',
                        values: skuNameArr
                    }
                ],
                onChange: function(p, v, dv) {
                    // getDetail();
                },
                onClose: function(p, v, d) {
                    getHis();
                }
            });
        },
        error: function (error) {

        }
    })
}
function getHis() {
    var customer = $("#customer").val();
    if(customer == ''){
        return;
    }
    $("#list").html("");
    tools.request({
        url:'/rongshun/orders/getHis',
        data:{customer : customer, status   : -1},
        success:function(res){
            if(res != null){
                $("#his").val(res["i"]);
                $("#paid").val(res["paid"]);
                if(res["list"] != null && res["list"].length > 0){
                    $("#dd").val(res["dd"]);
                    var tpl =
                        '{{#list}}' +
                        '<div class="weui-cell">' +
                        '<div class="weui-cell__bd">' +
                        '<p>{{skuName}} : {{priceOut}} * {{qty}} = {{my}}</p>' +
                        '</div>' +
                        '</div>' +
                        '{{/list}}';
                    var result = '<div class="list-header" style="color: #D74D49;height: 30px;line-height: 30px;padding-left: 15px;">订单明细:</div>';
                    result += tools.renderHtml(tpl, {list: res["list"]});
                    $('#list').append(result);
                }
            }
        }
    });
}
function addDetail() {
    var customer = $.trim($("#customer").val());
    if (customer == '') {
        $.toast("请先输入客户名称", "forbidden");
        return;
    }
    var dd = $.trim($("#dd").val());
    if (dd == '') {
        $.toast("请先选择开单日期", "forbidden");
        return;
    }
    window.open("inventoryOrdersPick.html?customer=" + customer + "&dd=" + dd, "_self");
}

function go() {
    var customer = $("#customer").val();
    var dd = $("#dd").val();
    var payable = $("#payable").val();
    if (customer == '') {
        $.toast("请先输入客户名称", "forbidden");
        return;
    }
    if (dd == '') {
        $.toast("请先选择开单日期", "forbidden");
        return;
    }
    if (payable == '') {
        $.toast("请先输入实付金额", "forbidden");
        return;
    }
    tools.request({
        url     : "/rongshun/orders/confirm",
        data    : {customer : customer, dd  : dd, payable   : payable},
        success : function (data, msg) {
            $.alert({
                title: '提示',
                text: msg,
                onOK: function () {
                    $("#customer").val('');
                    $("#dd").val('');
                    $("#payable").val('');
                    $("#his").val('');
                    $("#paid").val('');
                    $("#list").html("");
                }
            });
        }
    });
}

function getHisDetail() {
    var customer = $("#customer").val();
    if (customer == '') {
        $.toast("请先输入客户名称", "forbidden");
        return;
    }
    var his = $("#his").val();
    if(his == ""){
        $.toast("该客户无历史欠款", "forbidden");
        return;
    }

    window.open("hisDetail.html?customer=" + customer, "_self");
}