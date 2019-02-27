$(function(){
    var screen = $(window).height();
    $("#d").height(parseInt(screen) - 20);
    getSku();
});

function getSku(formData){
    $("#dg").datagrid({
        url:'/rongshun/weChatUserInfo/info',
        method: 'POST',
        queryParams:formData || '',
        height:'auto',
        fitColumns: true,
        fit: true,
        striped:true,
        border:true,
        singleSelect:true,
        showFooter: true,
        toolbar: '#tb',
        columns:[[{
            field:"id",
            title:"id",
            checkbox:true,
            width:30
        },{
            field:"skuName",
            title:"名称",
            align:'center',
            width:80
        }]]
    });
}

function build() {
    var rows = $("#dg").datagrid('getSelections');
    if(rows == ""){
        return;
    }
    var ids = "";
    for ( var i in rows) {
        ids.push(rows[i].id);
    }
    var skuName =  $("#skuName").textbox('getValue');
    if(skuName == ''){
        $.messager.alert("操作提示","请输入要组装的零件名称！","error");
        return;
    }
    var formData = {
        ids         : ids,
        skuName     : skuName

    };
    $.ajax({
        type:'Post',
        url:'/rongshun/sku/build',
        data:formData,
        dataType:'json',
        success:function(res){
            if(res != null){
                // getDetail(customer);
                $.messager.alert("操作提示",res.msg,"info", function () {
                    getSku();
                });
            }
        },
        error:function(){
            $.messager.alert("提示","数据错误，联系管理员","error");
        }
    });
}

